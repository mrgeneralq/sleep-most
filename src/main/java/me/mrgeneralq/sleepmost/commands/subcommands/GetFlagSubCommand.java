package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import org.apache.commons.lang.StringUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;

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

			this.messageService.sendMessage(sender, this.messageService.getMessage("&bPossible flags are: &e%flagsNames")
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
}
