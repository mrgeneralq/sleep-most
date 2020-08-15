package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.ICooldownService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {

    private final ICooldownService cooldownService;

    public PlayerQuitEventListener(ICooldownService cooldownService) {
        this.cooldownService = cooldownService;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){

        Player player = e.getPlayer();
        cooldownService.removeCooldown(player);
    }
}
