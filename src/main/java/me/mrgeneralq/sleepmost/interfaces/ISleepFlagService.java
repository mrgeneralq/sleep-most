package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.World;

public interface ISleepFlagService {
    void setFlag(World world, String flagName, Object value);
    Object getFlagValue(World world, String flagName);
    <T> ISleepFlag<T> getSleepFlag(String flagName);
}
