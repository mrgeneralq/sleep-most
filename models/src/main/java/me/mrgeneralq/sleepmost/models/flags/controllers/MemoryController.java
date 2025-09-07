package me.mrgeneralq.sleepmost.models.flags.controllers;

import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryController<V> extends AbstractFlagController<V>
{
    private final Map<UUID, V> valueInWorld = new HashMap<>();

    @Override
    public V getValueAt(World world)
    {
        return this.valueInWorld.get(world.getUID());
    }

    @Override
    public void setValueAt(World world, V value)
    {
        this.valueInWorld.put(world.getUID(), value);
    }
}
