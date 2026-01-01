package me.mrgeneralq.sleepmost.core.commands;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.interfaces.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ErrorCommand implements ISubCommand {

    private final IMessageService messageService;

    public ErrorCommand(IMessageService messageService){
        this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.UNKNOWN_COMMAND).build());
        return true;
    }
}
