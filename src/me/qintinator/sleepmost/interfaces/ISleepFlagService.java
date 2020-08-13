package me.qintinator.sleepmost.interfaces;

import org.bukkit.World;

public interface ISleepFlagService {
    void setFlag(World world, String flagName, Object value);
    Object getFlag(World world, String flagName);
    ISleepFlag getSleepFlag(String flagName);
}
