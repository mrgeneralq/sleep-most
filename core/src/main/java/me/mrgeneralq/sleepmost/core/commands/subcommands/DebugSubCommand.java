package me.mrgeneralq.sleepmost.core.commands.subcommands;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.interfaces.IDebugService;
import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.core.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugSubCommand implements ISubCommand {

    private final IDebugService debugService;
    private final IMessageService messageService;

    public DebugSubCommand(IDebugService debugService, IMessageService messageService) {
        this.debugService = debugService;
        this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender,messageService.getMessagePrefixed(MessageKey.NO_CONSOLE_COMMAND).build());
            return true;
        }

        Player player = (Player) sender;
        World world = CommandSenderUtils.getWorldOf(sender);

        boolean debuggingIsEnabled = this.debugService.isEnabledFor(player);

        if(debuggingIsEnabled)
            this.debugService.disableFor(player);
        else
            this.debugService.enableFor(player);

        this.messageService.sendMessage(player,
                this.messageService.getMessagePrefixed("&bDebug mode: %status%")
                        .setEnabledStatus(!debuggingIsEnabled).build());
        return true;
    }
}
