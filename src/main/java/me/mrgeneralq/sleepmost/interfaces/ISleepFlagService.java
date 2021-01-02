package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.enums.SleepmostFlag;
import org.bukkit.World;

public interface ISleepFlagService {
    void setFlag(World world, String flagName, Object value);
    Object getFlagValue(World world, String flagName);
    <T> ISleepFlag<T> getSleepFlag(String flagName);
    ISleepFlag<?> getFlag(SleepmostFlag flag, World world);
}
