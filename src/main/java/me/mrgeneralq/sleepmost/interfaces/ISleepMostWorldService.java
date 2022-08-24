package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import org.bukkit.World;

import java.util.Calendar;

public interface ISleepMostWorldService {

    void registerWorld(World world);
    void unregisterWorld(World world);
    void freezeTime(World world, Calendar freezeUntil);
    void unfreezeTime(World world);
    SleepMostWorld getWorld(World world);
    void updateWorld(SleepMostWorld sleepMostWorld);
    boolean worldExists(World world);

}
