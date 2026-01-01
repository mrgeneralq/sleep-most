package me.mrgeneralq.sleepmost.core.flags.types;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.serialization.IntegerSerialization;

public abstract class IntegerFlag extends AbstractFlag<Integer>
{
    public IntegerFlag(String name, String valueDescription, AbstractFlagController<Integer> controller, int defaultValue)
    {
        super(name, valueDescription, controller, IntegerSerialization.INSTANCE, defaultValue);
    }
}
