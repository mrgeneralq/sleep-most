package me.mrgeneralq.sleepmost.flags.controllers;

import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import org.bukkit.World;

public class ConfigFlagController<V> extends AbstractFlagController<V>
{
    private final IConfigRepository configRepository;

    public ConfigFlagController(IConfigRepository configRepository)
    {
        this.configRepository = configRepository;
    }

    @Override
    public V getValueAt(World world)
    {
        String stringValue = configRepository.getFlagValue(world, getFlag());

        return getFlag().parseValueFrom(stringValue);
    }

    @Override
    public void setValueAt(World world, V value)
    {
        this.configRepository.setFlagValue(world, getFlag(), value);
    }
}
