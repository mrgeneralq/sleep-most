package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetFlagSubCommand implements ISubCommand
{
	private final IMessageService messageService;
	private final IFlagsRepository flagsRepository;

	public GetFlagSubCommand(IMessageService messageService, IFlagsRepository flagsRepository) {
		this.messageService = messageService;
		this.flagsRepository = flagsRepository;
	}

	@Override
	public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{

		if(!CommandSenderUtils.hasWorld(sender)) {
			String noConsoleCmd = this.messageService.getMessagePrefixed(MessageKey.NO_CONSOLE_COMMAND)
					.build();
			this.messageService.sendMessage(sender, noConsoleCmd);
			return true;
		}
		
		World world = CommandSenderUtils.getWorldOf(sender);

		if(args.length != 2) {
			this.messageService.sendMessage(sender, messageService.getMessagePrefixed("&btype &e/sleepmost getflag <flag>").build());
			return true;
		}

		if (!this.flagsRepository.flagExists(args[1])) {
			this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed(MessageKey.FLAG_DOES_NOT_EXIST)
					.setFlag(args[1])
					.build());

			this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed("&bPossible flags are: &e%flagsNames")
					.setPlaceHolder("%flagsNames", StringUtils.join(this.flagsRepository.getFlagsNames(), ", "))
					.build());
			return true;
		}
		
		ISleepFlag<?> sleepFlag = this.flagsRepository.getFlag(args[1]);

		this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed(MessageKey.FLAG_SET_IN_WORLD)
				.setFlag(sleepFlag.getName())
				.setWorld(world)
				.setPlaceHolder("%value%", sleepFlag.getValueAt(world).toString())
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
		return new ArrayList<>();
	}
}
