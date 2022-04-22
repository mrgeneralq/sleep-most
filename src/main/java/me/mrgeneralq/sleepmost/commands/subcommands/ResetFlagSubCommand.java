package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.IFlagService;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;

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
			this.messageService.sendMessage(sender, this.messageService.getMessage(ConfigMessage.NO_CONSOLE_COMMAND).build());
			return true;
		}
		
		World world = CommandSenderUtils.getWorldOf(sender);

		if(args.length != 2) {
			this.messageService.sendMessage(sender, this.messageService.getMessage("&btype &e/sleepmost resetflag <flag>").build());
			return true;
		}

		if (!this.flagsRepository.flagExists(args[1])) {
			this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed("&cThis flag does not exist!").build());
			this.messageService.sendMessage(sender, this.messageService.getMessage("&bPossible flags are: &e%flagsNames")
					.setPlaceHolder("%flagsNames", StringUtils.join(this.flagsRepository.getFlagsNames(), ", "))
					.build());
			return true;
		}
		
		ISleepFlag<?> sleepFlag = this.flagsRepository.getFlag(args[1]);
		
		this.flagService.resetFlagAt(world, sleepFlag);

		this.messageService.sendMessage(sender, this.messageService.getMessagePrefixed("&bThe &e%flag% &bflag was reset to its default value &e%default-value%&b.")
				.setPlaceHolder("%flag%", sleepFlag.getName())
				.setPlaceHolder("%default-value%", sleepFlag.getValueAt(world).toString())
				.build());

		
		return true;
	}
}
