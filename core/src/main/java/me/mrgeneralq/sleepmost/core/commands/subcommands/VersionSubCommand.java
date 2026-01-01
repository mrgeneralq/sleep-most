package me.mrgeneralq.sleepmost.core.commands.subcommands;

import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.core.interfaces.IUpdateService;
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

        this.messageService.sendMessage(sender, messageService.getMessagePrefixed("&bYou are running version &c%version% &band the latest version is &c%latest-version%&b.")
                .setPlaceHolder("%version%", updateService.getCurrentVersion())
                .setPlaceHolder("%latest-version%", updateService.getCachedUpdateVersion())
                .build());
        return true;
    }

}
