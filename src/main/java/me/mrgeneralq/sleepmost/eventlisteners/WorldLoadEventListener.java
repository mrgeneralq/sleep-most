package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.IBossBarService;
import me.mrgeneralq.sleepmost.interfaces.IWorldPropertyService;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadEventListener implements Listener {

    IBossBarService bossBarService;
    private final IWorldPropertyService worldPropertyService;

    public WorldLoadEventListener(IBossBarService bossBarService, IWorldPropertyService worldPropertyService) {
        this.bossBarService = bossBarService;
        this.worldPropertyService = worldPropertyService;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e){

        if(ServerVersion.CURRENT_VERSION.supportsBossBars()){
            this.bossBarService.registerBossBar(e.getWorld());
        }

        if(!this.worldPropertyService.propertyExists(e.getWorld())){
            this.worldPropertyService.createNewWorldProperty(e.getWorld());
        }

    }
}
