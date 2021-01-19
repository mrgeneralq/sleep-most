package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import org.bukkit.World;

public interface IFlagService
{
    boolean isAfkFlagUsable();
    void reportIllegalValues();
    <V> void setStringValueAt(ISleepFlag<V> flag, World world, String stringValue);
}
