package me.mrgeneralq.sleepmost.abstracts;

import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import org.bukkit.World;

public abstract class AbstractFlag<V> implements ISleepFlag<V> {

    private String name;
    private String usage;

    public AbstractFlag(String name, String usage) {
        this.name = name;
        this.usage = usage;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUsage() {
        return this.usage;
    }


    @Override
    public V getValue(World world) {
        return null;
    }

    @Override
    public void setValue(World world, V value) {

    }
}
