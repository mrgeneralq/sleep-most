package me.qintinator.sleepmost.commands;

import me.qintinator.sleepmost.interfaces.ISubCommand;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ErrorCommand implements ISubCommand {

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        sender.sendMessage(Message.unknownCommand);
        return true;
    }
}
