package me.mrgeneralq.sleepmost.models.flags.types;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.serialization.IntegerSerialization;

public abstract class IntegerFlag extends AbstractFlag<Integer>
{
    public IntegerFlag(String name, String valueDescription, AbstractFlagController<Integer> controller, int defaultValue)
    {
        super(name, valueDescription, controller, IntegerSerialization.INSTANCE, defaultValue);
    }
}
