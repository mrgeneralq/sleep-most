package me.qintinator.sleepmost.eventlisteners;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.qintinator.sleepmost.interfaces.ISleepFlag;
import me.qintinator.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class OnEntitySpawn implements Listener {

    private final ISleepFlagService sleepFlagService;

    public OnEntitySpawn(ISleepFlagService sleepFlagService) {
    this.sleepFlagService = sleepFlagService;
    }


    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e){

        World world = e.getEntity().getWorld();

        if(e.getEntityType() != EntityType.PHANTOM)
        return;

        ISleepFlag<Boolean> preventPhantomFlag = sleepFlagService.getSleepFlag("prevent-phantom");

        if(!preventPhantomFlag.getValue(world))
            return;

        e.setCancelled(true);
    }
}
