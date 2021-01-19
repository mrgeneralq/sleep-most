package me.mrgeneralq.sleepmost.flags.types;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.IValueSerialization;
import org.bukkit.World;

public abstract class AbstractFlag<V> implements ISleepFlag<V>
{
    private final String name, valueDescription;
    private AbstractFlagController<V> controller;
    private final IValueSerialization<V> serialization;

    public AbstractFlag(String name, String valueDescription, AbstractFlagController<V> controller, IValueSerialization<V> serialization)
    {
        this.name = name;
        this.valueDescription = valueDescription;
        this.controller = controller;
        this.serialization = serialization;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getValueDescription()
    {
        return this.valueDescription;
    }

    public AbstractFlagController<V> getController()
    {
        return this.controller;
    }

    @Override
    public V getValueAt(World world)
    {
        return this.controller.getValueAt(world);
    }

    @Override
    public void setValueAt(World world, V value)
    {
        this.controller.setValueAt(world, value);
    }

    @Override
    public boolean isValidValue(Object value)
    {
        return this.serialization.parseValueFrom(value) != null;
    }

    @Override
    public IValueSerialization<V> getSerialization()
    {
        return this.serialization;
    }

    @Override
    public String getValueDisplayName(World world)
    {
        return this.controller.getValueAt(world).toString();
    }

    public void setController(AbstractFlagController<V> controller)
    {
        this.controller = controller;
    }
}