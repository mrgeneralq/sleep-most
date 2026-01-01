package me.mrgeneralq.sleepmost.core.commands;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.interfaces.*;
import me.mrgeneralq.sleepmost.core.models.SleepMostWorld;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SleepCommand implements CommandExecutor {
    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final ICooldownService cooldownService;
    private final IFlagsRepository flagsRepository;
    private final ISleepMostWorldService sleepMostWorldService;

    public SleepCommand(ISleepService sleepService, IMessageService messageService, ICooldownService cooldownService, IFlagsRepository flagsRepository, ISleepMostWorldService sleepMostWorldService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.cooldownService = cooldownService;
        this.flagsRepository = flagsRepository;
        this.sleepMostWorldService = sleepMostWorldService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.NO_CONSOLE_COMMAND).build());
            return true;
        }

        Player player = (Player) sender;

        if(this.sleepService.isEnabledAt(player.getWorld())){
            this.messageService.sendMessage(player, this.messageService.getMessagePrefixed(MessageKey.NOT_ENABLED_FOR_WORLD).build());
            return true;
        }


        if (!player.hasPermission("sleepmost.sleep")) {
            this.messageService.sendMessage(player, this.messageService.getMessagePrefixed(MessageKey.NO_PERMISSION_COMMAND).build());
            return true;
        }
        World world = player.getWorld();

        if(!this.flagsRepository.getSleepCmdFlag().getValueAt(world)){

            this.messageService.sendMessage(player, this.messageService.getMessagePrefixed(MessageKey.SLEEP_CMD_DISABLED).build());
            return true;
        }


        if(this.flagsRepository.getPreventSleepFlag().getValueAt(world)) {

            String sleepPreventedConfigMessage = messageService.getMessagePrefixed(MessageKey.SLEEP_PREVENTED)
                    .setPlayer(player)
                    .setWorld(world)
                    .build();

            this.messageService.sendMessage(player, messageService.getMessagePrefixed(sleepPreventedConfigMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());
            return true;
        }

        if (!this.sleepService.isSleepingPossible(world)) {
            this.messageService.sendMessage(player, messageService.getMessagePrefixed(MessageKey.CANNOT_SLEEP_NOW)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());
            return true;
        }

        SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);

        if(sleepMostWorld.isFrozen()){
            this.messageService.getMessagePrefixed(MessageKey.SLEEP_PREVENTED_LONGER_NIGHT)
                    .setWorld(world)
                    .setPlayer(player)
                    .build();
            return true;
        }


        boolean updatedSleepStatus = !this.sleepService.isPlayerAsleep(player);

        //update the sleeping status
        this.sleepService.setSleeping(player, updatedSleepStatus);

        // check if player is cooling down, if not send message to world and start cooldown of player
        if (cooldownService.cooldownEnabled() && !cooldownService.isCoolingDown(player)) {
            int sleepingPlayersAmount = sleepService.getSleepersAmount(world);
            int playersRequiredAmount = Math.round(sleepService.getRequiredSleepersCount(world));

            messageService.sendPlayerLeftMessage(player, sleepService.getCurrentSkipCause(world), sleepingPlayersAmount, playersRequiredAmount);
            cooldownService.startCooldown(player);
        }

        this.messageService.sendMessage(player, this.messageService.getMessagePrefixed(getConfigMessage(updatedSleepStatus))
                .setPlayer(player)
                .setWorld(world)
                .build());
        return true;
    }
    private MessageKey getConfigMessage(boolean sleepingStatus){
        return sleepingStatus ? MessageKey.SLEEP_SUCCESS : MessageKey.NO_LONGER_SLEEPING;
    }
}
