package me.qintinator.sleepmost.commands.subcommands;

import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.interfaces.ISubCommand;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements ISubCommand {

    private final ISleepService sleepService;

    public ReloadSubCommand(ISleepService sleepService) {
        this.sleepService = sleepService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        sleepService.reloadConfig();
        sender.sendMessage(Message.configReloaded);
        return true;
    }
}
