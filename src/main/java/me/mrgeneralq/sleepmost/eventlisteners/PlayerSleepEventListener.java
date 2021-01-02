package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.runnables.NightcycleAnimationTimer;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;


public class PlayerSleepEventListener implements Listener {

    private final Sleepmost main;
    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final ICooldownService cooldownService;
    private final ISleepFlagService sleepFlagService;
    private final DataContainer dataContainer;

    public PlayerSleepEventListener(Sleepmost main, ISleepService sleepService, IMessageService messageService, ICooldownService cooldownService, ISleepFlagService sleepFlagService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.cooldownService = cooldownService;
        this.sleepFlagService = sleepFlagService;
        this.main = main;
        this.dataContainer = DataContainer.getContainer();
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerSleep(PlayerBedEnterEvent e) {

        Player player = e.getPlayer();
        World world = player.getWorld();

        //used to calculate sleeping players different for lower versions
        if (ServerVersion.CURRENT_VERSION.sleepCalculatedDifferently())
            DataContainer.getContainer().addSleepingPlayer(player);


        if (!sleepService.enabledForWorld(world)) {
            return;
        }

        if (!sleepService.resetRequired(world)) {
            return;
        }

        if (dataContainer.getRunningWorldsAnimation().contains(world))
            return;


        ISleepFlag<Boolean> stormSleepFlag = sleepFlagService.getSleepFlag("storm-sleep");
        if (!stormSleepFlag.getValue(world) && world.isThundering()) {

            String preventSleepStormMessage = messageService.getConfigMessage(ConfigMessage.NO_SLEEP_THUNDERSTORM);

            String stormSkipMessage = messageService.getNewBuilder(preventSleepStormMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .usePrefix(false)
                    .build();

            player.sendMessage(stormSkipMessage);

        }

        // getting the sleep flag
        ISleepFlag<Boolean> preventSleepFlag = sleepFlagService.getSleepFlag("prevent-sleep");

        if (preventSleepFlag.getValue(world)) {

            String sleepPreventedConfigMessage = messageService.getConfigMessage(ConfigMessage.SLEEP_PREVENTED);
            String sleepPreventedMessage = messageService.getNewBuilder(sleepPreventedConfigMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .usePrefix(true)
                    .build();

            player.sendMessage(sleepPreventedMessage);
            e.setCancelled(true);
            return;
        }


        // check if player is cooling down, if not send message to world and start cooldown of player
        if (cooldownService.cooldownEnabled() && !cooldownService.isCoolingDown(player)) {
            messageService.sendPlayerLeftMessage(player, sleepService.getSleepSkipCause(world));
            cooldownService.startCooldown(player);
        }

        if (!sleepService.sleepPercentageReached(world))
            return;

        String lastSleeperName = e.getPlayer().getName();
        String lastSleeperDisplayName = e.getPlayer().getDisplayName();
        
		ISleepFlag<Boolean> nightCycleAnimation = sleepFlagService.getSleepFlag("nightcycle-animation");
		
        if (nightCycleAnimation.getValue(world)) {
        	if(world.isThundering() && !sleepService.isNight(world)){
        		sleepService.resetDay(world, lastSleeperName, lastSleeperDisplayName);
        		return;
			}

            //store running world
            dataContainer.getRunningWorldsAnimation().add(world);
            new NightcycleAnimationTimer(sleepService, messageService, world, lastSleeperName).runTaskTimer(main, 0, 1);
            return;
        }

        sleepService.resetDay(world, lastSleeperName, lastSleeperDisplayName);
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent e) {

        Player player = e.getPlayer();

        //used to calculate sleeping players different for lower versions
        if (ServerVersion.CURRENT_VERSION.sleepCalculatedDifferently())
            DataContainer.getContainer().removeSleepingPlayer(player);

    }
}
