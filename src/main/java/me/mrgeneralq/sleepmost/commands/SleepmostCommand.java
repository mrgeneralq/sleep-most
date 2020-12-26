package me.mrgeneralq.sleepmost.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import me.mrgeneralq.sleepmost.statics.SleepFlagMapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.mrgeneralq.sleepmost.commands.subcommands.DisableSubCommand;
import me.mrgeneralq.sleepmost.commands.subcommands.EnableSubCommand;
import me.mrgeneralq.sleepmost.commands.subcommands.InfoSubCommand;
import me.mrgeneralq.sleepmost.commands.subcommands.ReloadSubCommand;
import me.mrgeneralq.sleepmost.commands.subcommands.SetFlagCommand;
import me.mrgeneralq.sleepmost.commands.subcommands.VersionSubCommand;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import me.mrgeneralq.sleepmost.statics.Message;

public class SleepmostCommand implements CommandExecutor, TabCompleter {
	private final Map<String, ISubCommand> subCommands = new HashMap<>();

	private final ISleepService sleepService;
	private final IMessageService messageService;
	private final ISleepFlagService sleepFlagService;
	private final IUpdateService updateService;

	public SleepmostCommand(ISleepService sleepService, IMessageService messageService, ISleepFlagService sleepFlagService, IUpdateService updateService){
		this.sleepService = sleepService;
		this.messageService = messageService;
		this.sleepFlagService = sleepFlagService;
		this.updateService = updateService;

		this.registerSubCommands();
	}

	private void registerSubCommands(){
		subCommands.put("reload", new ReloadSubCommand(this.sleepService, this.messageService));
		subCommands.put("enable", new EnableSubCommand(this.sleepService,this.messageService));
		subCommands.put("disable", new DisableSubCommand(this.sleepService, this.messageService));
		subCommands.put("setflag", new SetFlagCommand(this.sleepService, this.messageService));
		subCommands.put("info", new InfoSubCommand(this.sleepService, this.messageService, this.sleepFlagService));
		subCommands.put("version", new VersionSubCommand(this.updateService, this.messageService));
	}




	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

		if(args.length == 0){

			if(!sender.hasPermission("sleepmost.help")){
				messageService.sendMessage(sender, Message.NO_PERMISSION, false);
				return true;
			}
			messageService.sendMessage(sender, "&b*********************************************", false);
			messageService.sendMessage(sender, "&b*&e  SLEEPMOST &o&7author: MrGeneralQ  &b*", false);
			messageService.sendMessage(sender, "&b*********************************************", false);
			messageService.sendMessage(sender, "", false);
			messageService.sendMessage(sender, "&e/sm &fshow a list of available commands", false);
			messageService.sendMessage(sender, "&e/sm enable &fenable sleepmost in the current world", false);
			messageService.sendMessage(sender, "&e/sm disable &fdisable sleepmost in the current world", false);
			messageService.sendMessage(sender, "&e/sm setflag <flagname> <flagvalue> &fset a flag for the current world", false);
			messageService.sendMessage(sender, "&e/sm info &fshow a list of all flags set for your world", false);
			messageService.sendMessage(sender, "&e/sm version &fshow the current version of sleep most", false);
			messageService.sendMessage(sender, "&e/sm reload &freload the config file", false);
			return true;
		}


		String subCommand = args[0];

		// check if player has permission of command
		if(!sender.hasPermission("sleepmost." + subCommand))
		{
			
			messageService.sendMessage(sender, Message.NO_PERMISSION, false);
			return true;
		}

		return subCommands.getOrDefault(subCommand, new ErrorCommand(messageService)).executeCommand(sender, command, commandLabel, args);
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {


		if(args.length < 1)
			return null;


		String arg1 = args[0];

		//if(!commandSender.hasPermission(String.format("sleepmost.%s", arg1)))
		//    return null;


		if(args.length == 1){
			List<String> subCommands = this.subCommands.keySet().stream().filter(subCmd -> commandSender.hasPermission("sleepmost." + subCmd)).collect(Collectors.toList());
			Collections.sort(subCommands);
			return subCommands;
		}


		if(args[0].equalsIgnoreCase("setflag") && args.length == 2)
		{
			List<String> flags = SleepFlagMapper.getMapper().getAllFlags();
			Collections.sort(flags);
			return flags;
		}


		return null;
	}
}
