package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class VersionSubCommand implements ISubCommand {


    private final IUpdateService updateService;
    private final IMessageService messageService;

    public VersionSubCommand(IUpdateService updateService, IMessageService messageService) {
        this.updateService = updateService;
        this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        messageService.sendMessage(sender, "&bYou are running version &c" +  updateService.getCurrentVersion(), true);
        return true;
    }
}
