package me.qintinator.sleepmost.commands;

import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISubCommand;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ErrorCommand implements ISubCommand {

    private final IMessageService messageService;

    public ErrorCommand(IMessageService messageService){
        this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        messageService.sendMessage(sender, Message.unknownCommand, true);
        return true;
    }
}
