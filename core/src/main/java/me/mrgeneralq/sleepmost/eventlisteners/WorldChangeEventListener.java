package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeEventListener implements Listener
{
    private final ISleepService sleepService;

    public WorldChangeEventListener(ISleepService sleepService) {
        this.sleepService = sleepService;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        this.sleepService.setSleeping(player, false);
    }
}
