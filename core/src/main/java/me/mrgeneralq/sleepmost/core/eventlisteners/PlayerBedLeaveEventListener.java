package me.mrgeneralq.sleepmost.core.eventlisteners;
import me.mrgeneralq.sleepmost.core.interfaces.ISleepService;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class PlayerBedLeaveEventListener implements Listener {


    private final ISleepService sleepService;
    public PlayerBedLeaveEventListener(ISleepService sleepService) {
        this.sleepService = sleepService;
    }

    @EventHandler
    void onPlayerBedLeave(PlayerBedLeaveEvent e){

        Player player = e.getPlayer();
        this.sleepService.setSleeping(player, false);
    }

}
