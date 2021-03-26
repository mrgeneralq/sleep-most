package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class PlayerSleepEventListener implements Listener {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final ICooldownService cooldownService;
    private final DataContainer dataContainer;
    private final IFlagsRepository flagsRepository;

    public PlayerSleepEventListener(ISleepService sleepService, IMessageService messageService, ICooldownService cooldownService, IFlagsRepository flagsRepository) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.cooldownService = cooldownService;
        this.flagsRepository = flagsRepository;
        this.dataContainer = DataContainer.getContainer();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerSleep(PlayerBedEnterEvent e) {

        Player player = e.getPlayer();
        World world = player.getWorld();


        if (!sleepService.isEnabledAt(world)) {
            return;
        }

        if (!sleepService.resetRequired(world)) {
            return;
        }

        if (dataContainer.isAnimationRunningAt(world))
            return;

        if (world.isThundering() && !this.flagsRepository.getStormSleepFlag().getValueAt(world)) {

            String preventSleepStormMessage = messageService.getConfigMessage(ConfigMessage.NO_SLEEP_THUNDERSTORM);

            this.messageService.sendMessage(player, messageService.newBuilder(preventSleepStormMessage)
                    .usePrefix(false)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());

            e.setCancelled(true);
            return;
        }

        if(this.flagsRepository.getPreventSleepFlag().getValueAt(world)) {

            String sleepPreventedConfigMessage = messageService.getConfigMessage(ConfigMessage.SLEEP_PREVENTED);

            this.messageService.sendMessage(player, messageService.newPrefixedBuilder(sleepPreventedConfigMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());

            e.setCancelled(true);
            return;
        }

        // check if player is cooling down, if not send message to world and start cooldown of player
        if (cooldownService.cooldownEnabled() && cooldownService.isCoolingDown(player)) {
            e.setCancelled(true);
            return;
        }
        cooldownService.startCooldown(player);
        this.sleepService.setSleeping(player , true);

        int sleepingPlayersAmount = sleepService.getSleepersAmount(world);
        int playersRequiredAmount = Math.round(sleepService.getRequiredSleepersCount(world));
        messageService.sendPlayerLeftMessage(player, sleepService.getCurrentSkipCause(world), sleepingPlayersAmount, playersRequiredAmount);
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent e) {
        Player player = e.getPlayer();
        sleepService.setSleeping(player, false);
    }
}
