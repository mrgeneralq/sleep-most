package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public BedSubCommand(ISleepService sleepService, IMessageService messageService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender, messageService.getMessage(MessageKey.NO_CONSOLE_COMMAND).build());
            return true;
        }


        World world = CommandSenderUtils.getWorldOf(sender);


        if (!sleepService.isEnabledAt(world)) {
            this.messageService.sendMessage(sender, messageService.getMessage(MessageKey.ALREADY_DISABLED_FOR_WORLD).build());
            return true;
        }

        Player player = (Player) sender;

        if (player.getBedSpawnLocation() == null) {
            this.messageService.sendMessage(player, messageService.getMessage(MessageKey.NO_BED_LOCATION_SET)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());
            return true;
        }

        player.teleport(player.getBedSpawnLocation());
        this.messageService.sendMessage(player, messageService.getMessage(MessageKey.TELEPORTED_TO_BED)
                .setPlayer(player)
                .setWorld(world)
                .build());
        return true;
    }
}

