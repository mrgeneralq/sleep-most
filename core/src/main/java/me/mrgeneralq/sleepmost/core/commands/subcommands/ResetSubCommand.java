package me.mrgeneralq.sleepmost.core.commands.subcommands;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.interfaces.IFlagService;
import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.core.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ResetSubCommand implements ISubCommand
{
    private final IMessageService messageService;
    private final IFlagService flagService;

    public ResetSubCommand(IMessageService messageService, IFlagService flagService) {
        this.messageService = messageService;
        this.flagService = flagService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.NO_CONSOLE_COMMAND).build());
            return true;
        }
        World world = CommandSenderUtils.getWorldOf(sender);
        this.flagService.resetFlagsAt(world);

        this.messageService.sendMessage(sender,
                this.messageService.getMessagePrefixed(MessageKey.ALL_FLAGS_RESET_SUCCESS)
                        .setWorld(world)
                        .build());
        return true;
    }
}
