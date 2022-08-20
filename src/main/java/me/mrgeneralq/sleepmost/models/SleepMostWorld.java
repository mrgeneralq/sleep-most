package me.mrgeneralq.sleepmost.models;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.UUID;

public class SleepMostWorld {

    private final UUID worldUUID;


    public SleepMostWorld(World world){
        this.worldUUID = world.getUID();
    }

    public World getWorld(){
        return Bukkit.getWorld(this.worldUUID);
    }



}
