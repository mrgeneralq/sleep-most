package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.IBossBarService;
import me.mrgeneralq.sleepmost.interfaces.ISleepMostWorldService;
import me.mrgeneralq.sleepmost.interfaces.IWorldPropertyService;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldUnloadEventListener implements Listener {

    IBossBarService bossBarService;
    private final IWorldPropertyService worldPropertyService;
    private final ISleepMostWorldService sleepMostWorldService;

    public WorldUnloadEventListener(IBossBarService bossBarService, IWorldPropertyService worldPropertyService, ISleepMostWorldService sleepMostWorldService) {
        this.bossBarService = bossBarService;
        this.worldPropertyService = worldPropertyService;
        this.sleepMostWorldService = sleepMostWorldService;
    }

    @EventHandler
    public void onWorldLoad(WorldUnloadEvent e) {

        World world = e.getWorld();
        this.sleepMostWorldService.unregisterWorld(world);

    }
}
