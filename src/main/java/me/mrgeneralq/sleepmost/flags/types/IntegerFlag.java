package me.mrgeneralq.sleepmost.flags.types;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.IntegerSerialization;

public class IntegerFlag extends AbstractFlag<Integer>
{
    public IntegerFlag(String name, String valueDescription, AbstractFlagController<Integer> controller)
    {
        super(name, valueDescription, controller, IntegerSerialization.INSTANCE);
    }
}
