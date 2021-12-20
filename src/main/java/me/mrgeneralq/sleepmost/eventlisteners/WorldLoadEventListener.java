package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.IBossBarService;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadEventListener implements Listener {

    IBossBarService bossBarService;

    public WorldLoadEventListener(IBossBarService bossBarService) {
        this.bossBarService = bossBarService;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e){

        if(!ServerVersion.CURRENT_VERSION.supportsBossBars())
            return;

        this.bossBarService.registerBossBar(e.getWorld());
    }

}
