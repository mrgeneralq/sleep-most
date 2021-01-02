package me.mrgeneralq.sleepmost.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.mrgeneralq.sleepmost.commands.subcommands.*;
import me.mrgeneralq.sleepmost.enums.MessageTemplate;
import me.mrgeneralq.sleepmost.mappers.SleepFlagMapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;

import static java.util.stream.Collectors.toList;
import static me.mrgeneralq.sleepmost.statics.ChatColorUtils.colorize;

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

		//enable when debugging
		//subCommands.put("test", new TestCommand());
	}




	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

		if(args.length == 0){

			if(!sender.hasPermission("sleepmost.help")){
				sender.sendMessage(messageService.fromTemplate(MessageTemplate.NO_PERMISSION));
				return true;
			}
			sender.sendMessage(colorize("&b*********************************************"));
			sender.sendMessage(colorize("&b*&e  SLEEPMOST &o&7author: MrGeneralQ  &b*"));
			sender.sendMessage(colorize("&b*********************************************"));
			sender.sendMessage(colorize("&e/sm &fshow a list of available commands"));
			sender.sendMessage(colorize("&e/sm enable &fenable sleepmost in the current world"));
			sender.sendMessage(colorize("&e/sm disable &fdisable sleepmost in the current world"));
			sender.sendMessage(colorize("&e/sm setflag <flagname> <flagvalue> &fset a flag for the current world"));
			sender.sendMessage(colorize("&e/sm info &fshow a list of all flags set for your world"));
			sender.sendMessage(colorize("&e/sm version &fshow the current version of sleep most"));
			sender.sendMessage(colorize("&e/sm reload &freload the config file"));
			return true;
		}


		String subCommand = args[0];

		// check if player has permission of command
		if(!sender.hasPermission("sleepmost." + subCommand))
		{
			sender.sendMessage(messageService.fromTemplate(MessageTemplate.NO_PERMISSION));
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
			//return the subcommands the player has permissions to execute
			return this.subCommands.keySet().stream()
					.filter(subCmd -> commandSender.hasPermission("sleepmost." + subCmd))
					.sorted()
					.collect(toList());
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
