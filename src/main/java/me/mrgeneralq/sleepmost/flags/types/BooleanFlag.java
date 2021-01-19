package me.mrgeneralq.sleepmost.flags.types;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.BooleanSerialization;

public abstract class BooleanFlag extends AbstractFlag<Boolean>
{
    public BooleanFlag(String name, String valueDescription, AbstractFlagController<Boolean> controller, boolean defaultValue)
    {
        super(name, valueDescription, controller, BooleanSerialization.INSTANCE, defaultValue);
    }
    public BooleanFlag(String name, AbstractFlagController<Boolean> controller, boolean defaultValue)
    {
        this(name, "<true, false>", controller, defaultValue);
    }
}
