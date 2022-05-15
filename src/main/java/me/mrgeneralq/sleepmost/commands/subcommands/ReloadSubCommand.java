package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.IFlagService;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final IFlagService flagService;

    public ReloadSubCommand(ISleepService sleepService, IMessageService messageService, IFlagService flagService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.flagService = flagService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        //reload the config
        this.sleepService.reloadConfig();
        this.messageService.reloadConfig();
        sender.sendMessage(messageService.getMessagePrefixed(MessageKey.CONFIG_RELOADED).build());

        //handle somehow illegal flags values
        this.flagService.handleProblematicFlags();
        return true;
    }
}
