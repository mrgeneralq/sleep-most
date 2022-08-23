package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.World;

import java.util.Calendar;

public interface ISleepMostWorldService {

    void registerWorld(World world);
    void unregisterWorld(World world);
    void freezeTime(World world);
    boolean isFrozen(World world);
    Calendar getFrozenSince(World world);


}
