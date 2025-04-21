package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.models.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final IFlagsRepository flagsRepository;

    public KickSubCommand(ISleepService sleepService, IMessageService messageService, IFlagsRepository flagsRepository) {
    this.sleepService = sleepService;
    this.messageService = messageService;
        this.flagsRepository = flagsRepository;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.NO_CONSOLE_COMMAND).build());
            return true;
        }

        Player player = (Player) sender;
        World world = CommandSenderUtils.getWorldOf(sender);


        boolean kickingEnabled = this.flagsRepository.getAllowKickFlag().getValueAt(world);

        if(!kickingEnabled){
            MessageBuilder kickNotAllowedMsg = this.messageService.getMessagePrefixed(MessageKey.KICKING_NOT_ALLOWED)
                    .setWorld(world);

            this.messageService.sendMessage(player , kickNotAllowedMsg.build());
            return true;
        }

        if(args.length < 2){
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.SPECIFY_PLAYER).build());
            return true;
        }

        String targetPlayerName = args[1];

        if(Bukkit.getPlayer(targetPlayerName) == null){
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.TARGET_NOT_ONLINE)
                    .setPlayer(player)
                    .build());
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        if(!this.sleepService.isPlayerAsleep(targetPlayer)){
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.TARGET_NOT_SLEEPING)
                    .setPlayer(targetPlayer)
                    .build());
            return true;
        }



        MessageBuilder targetKickedFromBedMsg = this.messageService.getMessagePrefixed(MessageKey.KICKED_PLAYER_FROM_BED)
                .setPlayer(targetPlayer)
                .setWorld(targetPlayer.getWorld());

        MessageBuilder youAreKickedMsg = this.messageService.getMessagePrefixed(MessageKey.YOU_ARE_KICKED_FROM_BED)
                .setPlayer(player);

        targetPlayer.teleport(targetPlayer.getLocation());
        this.messageService.sendMessage(sender,targetKickedFromBedMsg.setPlayer(targetPlayer).build());
        this.messageService.sendMessage(targetPlayer, youAreKickedMsg.setPlayer(player).build());
        this.sleepService.setSleeping(targetPlayer, false);
        return true;
    }
}
