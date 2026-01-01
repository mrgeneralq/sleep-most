package me.mrgeneralq.sleepmost.core.commands.subcommands;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.flags.types.TabCompletedFlag;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.mrgeneralq.sleepmost.core.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.core.interfaces.IFlagService;
import me.mrgeneralq.sleepmost.core.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.core.statics.CommandSenderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResetFlagSubCommand implements ISubCommand
{
	private final IMessageService messageService;
	private final IFlagsRepository flagsRepository;
	private final IFlagService flagService;

	public ResetFlagSubCommand(IMessageService messageService, IFlagsRepository flagsRepository, IFlagService flagService) {
		this.messageService = messageService;
		this.flagsRepository = flagsRepository;
		this.flagService = flagService;
	}
	
	@Override
	public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(!CommandSenderUtils.hasWorld(sender)) {
			this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed(MessageKey.NO_CONSOLE_COMMAND).build());
			return true;
		}
		
		World world = CommandSenderUtils.getWorldOf(sender);

		if(args.length != 2) {
			this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed("&btype &e/sleepmost resetflag <flag>").build());
			return true;
		}

		if (!this.flagsRepository.flagExists(args[1])) {
			this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed(MessageKey.FLAG_DOES_NOT_EXIST).setFlag(args[1]).build());
			this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed("&bPossible flags are: &e%flagsNames")
					.setPlaceHolder("%flagsNames", StringUtils.join(this.flagsRepository.getFlagsNames(), ", "))
					.build());
			return true;
		}
		
		ISleepFlag<?> sleepFlag = this.flagsRepository.getFlag(args[1]);
		
		this.flagService.resetFlagAt(world, sleepFlag);

		this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed(MessageKey.FLAG_RESET_IN_WORLD)
				.setFlag(sleepFlag.getName())
				.setPlaceHolder("%default-value%", sleepFlag.getValueAt(world).toString())
				.build());

		return true;
	}

	@Override
	public List<String> tabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, List<String> args) {
		if (args.size() == 1) {
			return this.flagsRepository.getFlagsNames().stream()
					.filter(flag -> flag.contains(args.get(0)) || flag.equalsIgnoreCase(args.get(0)))
					.collect(Collectors.toList());
		}

		if (args.size() == 2) {
			ISleepFlag<?> flag = flagsRepository.getFlags().stream()
					.filter(f -> f.getName().equalsIgnoreCase(args.get(0)))
					.findFirst().orElse(null);

			if (flag instanceof TabCompletedFlag<?>) {
				return ((TabCompletedFlag<?>) flag).tabComplete(sender, command, alias, args.subList(1, args.size()));
			}
		}
		return new ArrayList<>();
	}
}
