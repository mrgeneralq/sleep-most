package me.mrgeneralq.sleepmost.commands;

import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ErrorCommand implements ISubCommand {

    private final IMessageService messageService;

    public ErrorCommand(IMessageService messageService){
        this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage(messageService.fromTemplate(MessageTemplate.UNKNOWN_COMMAND));
        return true;
    }
}
