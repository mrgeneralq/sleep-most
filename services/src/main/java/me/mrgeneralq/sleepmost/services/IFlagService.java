package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import org.bukkit.World;

public interface IFlagService
{
    boolean isAfkFlagUsable();
    void handleProblematicFlags();
    <V> void setStringValueAt(ISleepFlag<V> flag, World world, String stringValue);
    <V> String getValueDisplayName(ISleepFlag<V> flag, Object value);
    void resetFlagsAt(World world);
    <V> void resetFlagAt(World world, ISleepFlag<V> flag);
}
