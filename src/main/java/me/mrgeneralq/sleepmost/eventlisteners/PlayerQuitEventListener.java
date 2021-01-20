package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.ICooldownService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {

    private final ICooldownService cooldownService;
    private final ISleepService sleepService;

    public PlayerQuitEventListener(ICooldownService cooldownService, ISleepService sleepService) {
        this.cooldownService = cooldownService;
        this.sleepService = sleepService;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();

        this.cooldownService.removeCooldown(player);
        this.sleepService.setSleeping(player, false);
    }
}
