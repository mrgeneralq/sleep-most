package me.mrgeneralq.sleepmost.models.flags.controllers;

import me.mrgeneralq.sleepmost.models.flags.ISleepFlag;
import org.bukkit.World;

public abstract class AbstractFlagController<V>
{
    protected ISleepFlag<V> flag;

    //Internal use only, to avoid circular dependency
    public void setFlag(ISleepFlag<V> flag)
    {
        this.flag = flag;
    }
    public abstract V getValueAt(World world);
    public abstract void setValueAt(World world, V value);
}
