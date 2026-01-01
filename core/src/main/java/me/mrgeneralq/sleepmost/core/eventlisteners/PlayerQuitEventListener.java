package me.mrgeneralq.sleepmost.core.eventlisteners;

import me.mrgeneralq.sleepmost.core.interfaces.IBossBarService;
import me.mrgeneralq.sleepmost.core.interfaces.ICooldownService;
import me.mrgeneralq.sleepmost.core.interfaces.ISleepMostPlayerService;
import me.mrgeneralq.sleepmost.core.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.core.statics.ServerVersion;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {

    private final ICooldownService cooldownService;
    private final ISleepService sleepService;
    private final IBossBarService bossBarService;
    private final ISleepMostPlayerService sleepMostPlayerService;

    public PlayerQuitEventListener(ICooldownService cooldownService, ISleepService sleepService, IBossBarService bossBarService, ISleepMostPlayerService sleepMostPlayerService) {
        this.cooldownService = cooldownService;
        this.sleepService = sleepService;
        this.bossBarService = bossBarService;
        this.sleepMostPlayerService = sleepMostPlayerService;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){

        Player player = e.getPlayer();

        if(ServerVersion.CURRENT_VERSION.supportsBossBars())
        this.bossBarService.unregisterPlayer(player.getWorld(), player);

        this.cooldownService.removeCooldown(player);
        this.sleepService.setSleeping(player, false);
    }
}
