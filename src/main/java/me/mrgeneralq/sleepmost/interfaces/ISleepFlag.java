package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.World;

public interface ISleepFlag<V> {

    public String getName();
    public String getUsage();
    public boolean isValidValue(String value);
    public V getValue(World world);
    public void setValue(World world, V value);
}
