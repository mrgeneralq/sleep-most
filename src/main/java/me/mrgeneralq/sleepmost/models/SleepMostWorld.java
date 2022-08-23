package me.mrgeneralq.sleepmost.models;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Calendar;
import java.util.UUID;

public class SleepMostWorld {

    private final UUID worldUUID;
    private boolean frozen = false;
    private Calendar frozenSince = null;


    public SleepMostWorld(World world){
        this.worldUUID = world.getUID();
    }

    public World getWorld(){
        return Bukkit.getWorld(this.worldUUID);
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;

        if(frozen)
            this.frozenSince = Calendar.getInstance();
        else
            this.frozenSince = null;
    }

    public Calendar getFrozenSince() {
        return this.frozenSince;
    }
}
