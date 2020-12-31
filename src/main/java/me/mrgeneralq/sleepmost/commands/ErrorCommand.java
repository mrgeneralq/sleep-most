package me.mrgeneralq.sleepmost.commands;

import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.ChatColorUtils;
import me.mrgeneralq.sleepmost.statics.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ErrorCommand implements ISubCommand {

    private final IMessageService messageService;

    public ErrorCommand(IMessageService messageService){
        this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        sender.sendMessage(ChatColorUtils.colorize(Message.UNKNOWN_COMMAND));
        return true;
    }
}
