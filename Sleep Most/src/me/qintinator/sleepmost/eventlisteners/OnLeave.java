package me.qintinator.sleepmost.eventlisteners;

import me.qintinator.sleepmost.interfaces.ICooldownService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeave implements Listener {

    private final ICooldownService cooldownService;

    public OnLeave(ICooldownService cooldownService) {
        this.cooldownService = cooldownService;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){

        Player player = e.getPlayer();
        cooldownService.removeCooldown(player);
    }
}
