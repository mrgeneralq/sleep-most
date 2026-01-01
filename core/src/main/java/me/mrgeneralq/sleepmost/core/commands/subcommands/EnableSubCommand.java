package me.mrgeneralq.sleepmost.core.commands.subcommands;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.core.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.core.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EnableSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public EnableSubCommand(ISleepService sleepService, IMessageService messageService) {
    this.sleepService = sleepService;
    this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender,messageService.getMessagePrefixed(MessageKey.NO_CONSOLE_COMMAND).build());
            return true;
        }

        World world = CommandSenderUtils.getWorldOf(sender);

        if(this.sleepService.isEnabledAt(world)){
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.ALREADY_ENABLED_FOR_WORLD)
                    .setWorld(world)
                    .build());
           return true;
        }

        this.sleepService.enableAt(world);
        this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.ENABLED_FOR_WORLD)
                .setWorld(world)
                .build());
        return true;
    }
}
