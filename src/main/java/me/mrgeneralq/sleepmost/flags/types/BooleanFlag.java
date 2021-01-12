package me.mrgeneralq.sleepmost.flags.types;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.BooleanSerialization;

public class BooleanFlag extends AbstractFlag<Boolean>
{
    public BooleanFlag(String name, String valueDescription, AbstractFlagController<Boolean> controller)
    {
        super(name, valueDescription, controller, BooleanSerialization.INSTANCE);
    }
    public BooleanFlag(String name, AbstractFlagController<Boolean> controller)
    {
        this(name, "<true, false>", controller);
    }
}
