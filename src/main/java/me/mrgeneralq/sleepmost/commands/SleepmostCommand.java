package me.mrgeneralq.sleepmost.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.mrgeneralq.sleepmost.commands.subcommands.*;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import static java.util.stream.Collectors.toList;
import static me.mrgeneralq.sleepmost.statics.ChatColorUtils.colorize;

public class SleepmostCommand implements CommandExecutor, TabCompleter {
	private final Map<String, ISubCommand> subCommands = new HashMap<>();

	private final ISleepService sleepService;
	private final IMessageService messageService;
	private final IUpdateService updateService;
	private final IFlagService flagService;
	private final IFlagsRepository flagsRepository;
	private final IConfigRepository configRepository;

	public SleepmostCommand(ISleepService sleepService, IMessageService messageService, IUpdateService updateService, IFlagService flagService, IFlagsRepository flagsRepository, IConfigRepository configRepository){
		this.sleepService = sleepService;
		this.messageService = messageService;
		this.updateService = updateService;
		this.flagService = flagService;
		this.flagsRepository = flagsRepository;
		this.configRepository = configRepository;

		this.registerSubCommands();
	}

	private void registerSubCommands(){
		subCommands.put("reload", new ReloadSubCommand(this.sleepService, this.messageService, this.flagService));
		subCommands.put("enable", new EnableSubCommand(this.sleepService,this.messageService));
		subCommands.put("disable", new DisableSubCommand(this.sleepService, this.messageService));
		subCommands.put("setflag", new SetFlagCommand(this.sleepService, this.messageService, this.flagService, this.flagsRepository));
		subCommands.put("info", new InfoSubCommand(this.sleepService, this.messageService, this.flagService, this.flagsRepository));
		subCommands.put("version", new VersionSubCommand(this.updateService, this.messageService));
		subCommands.put("reset", new ResetSubCommand(this.messageService, this.flagService));
		subCommands.put("setops", new SetOnePlayerSleepCommand(this.sleepService, this.messageService,this.flagService, this.flagsRepository));
		subCommands.put("bed", new BedSubCommand(this.sleepService,this.messageService));

		//enable when debugging
		//subCommands.put("test", new TestCommand(this.messageService, this.flagsRepository, this.configRepository));
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

		if(args.length == 0){

			if(!sender.hasPermission("sleepmost.help")){
				this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.NO_PERMISSION));
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
			sender.sendMessage(colorize("&e/sm reset &fset the flags in your world to their default values"));
			sender.sendMessage(colorize("&e/sm setops &fenable one player sleep for the current world"));
			sender.sendMessage(colorize("&e/sm bed &fteleport to your current bed spawn location"));
			sender.sendMessage(colorize("&e/sm reload &freload the config file"));
			return true;
		}


		String subCommandStr = args[0].toLowerCase();

		// check if player has permission of command
		if(!sender.hasPermission("sleepmost." + subCommandStr))
		{
			this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.NO_PERMISSION));
			return true;
		}

		ISubCommand subCommand = subCommands.getOrDefault(subCommandStr, new ErrorCommand(messageService));

		return subCommand.executeCommand(sender, command, commandLabel, args);
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
			return this.flagsRepository.getFlagsNames();
		}
		return null;
	}
}
