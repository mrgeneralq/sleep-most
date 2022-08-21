package me.mrgeneralq.sleepmost.commands;

import java.util.*;

import me.mrgeneralq.sleepmost.commands.subcommands.*;
import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

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
	private final ICooldownService cooldownService;
	private final IBossBarService bossBarService;
	private final IWorldPropertyService worldPropertyService;
	private final ISleepMostPlayerService sleepMostPlayerService;
	private final IInsomniaService insomniaService;
	private final IDebugService debugService;

	public SleepmostCommand(
			ISleepService sleepService,
			IMessageService messageService,
			IUpdateService updateService,
			IFlagService flagService,
			IFlagsRepository flagsRepository,
			IConfigRepository configRepository,
			ICooldownService cooldownService,
			IBossBarService bossBarService,
			IWorldPropertyService worldPropertyService,
			ISleepMostPlayerService sleepMostPlayerService,
			IInsomniaService insomniaService,
			IDebugService debugService
	){
		this.sleepService = sleepService;
		this.messageService = messageService;
		this.updateService = updateService;
		this.flagService = flagService;
		this.flagsRepository = flagsRepository;
		this.configRepository = configRepository;
		this.cooldownService = cooldownService;
		this.bossBarService = bossBarService;
		this.worldPropertyService = worldPropertyService;
		this.sleepMostPlayerService = sleepMostPlayerService;
		this.insomniaService = insomniaService;
		this.debugService = debugService;
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
		subCommands.put("sleep", new SleepSubCommand(this.sleepService,this.flagsRepository,this.messageService,this.cooldownService, this.bossBarService, this.worldPropertyService, this.insomniaService));
		subCommands.put("kick", new KickSubCommand(this.sleepService,this.messageService, this.flagsRepository));
		subCommands.put("insomnia", new InsomniaSubCommand(this.sleepService, this.flagsRepository, this.messageService, this.worldPropertyService, this.sleepMostPlayerService, this.insomniaService));
		subCommands.put("getflag", new GetFlagSubCommand(this.messageService, this.flagsRepository));
		subCommands.put("resetflag", new ResetFlagSubCommand(this.messageService, this.flagsRepository, this.flagService));
		subCommands.put("debug", new DebugSubCommand(this.debugService, this.messageService));

		//enable when debugging
		//subCommands.put("test", new TestCommand(this.messageService, this.flagsRepository, this.configRepository));
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

		if(args.length == 0){

			if(!sender.hasPermission("sleepmost.help")){
				this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.NO_PERMISSION_COMMAND).build());
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
			sender.sendMessage(colorize("&e/sm sleep &fput yourself in sleep status"));
			sender.sendMessage(colorize("&e/sm kick &fkick a player from the bed"));
			sender.sendMessage(colorize("&e/sm insomnia &fBlock sleeping for the current night"));
			sender.sendMessage(colorize("&e/sm getflag &fQuickly grab a flag's value in your world"));
			sender.sendMessage(colorize("&e/sm debug &fToggle debugging mode for your player"));
			return true;
		}


		String subCommandStr = args[0].toLowerCase();

		// check if player has permission of command
		if(!sender.hasPermission("sleepmost." + subCommandStr))
		{
			this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.NO_PERMISSION_COMMAND).build());
			return true;
		}

		ISubCommand subCommand = subCommands.getOrDefault(subCommandStr, new ErrorCommand(messageService));

		return subCommand.executeCommand(sender, command, commandLabel, args);
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
		if (args.length < 1) return null;

		if (args.length == 1) {
			//return the subcommands the player has permissions to execute
			return this.subCommands.keySet().stream()
					.filter(subCmd -> sender.hasPermission("sleepmost." + subCmd))
					.filter(subCmd -> subCmd.contains(args[0]) || subCmd.equalsIgnoreCase(args[0]))
					.sorted()
					.collect(toList());
		}

		String cmdName = this.subCommands.keySet().stream()
				.filter(subCmd -> subCmd.equalsIgnoreCase(args[0]))
				.findFirst().orElse(null);

		if (cmdName == null) return new ArrayList<>();

		if (this.subCommands.containsKey(cmdName)) {
			return this.subCommands.get(cmdName).tabComplete(sender, command, alias, Arrays.asList(args).subList(1, args.length));
		}

		return new ArrayList<>();
	}
}
