package me.mrgeneralq.sleepmost.core.flags.types;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.serialization.DoubleSerialization;

public abstract class DoubleFlag extends AbstractFlag<Double>
{
    public DoubleFlag(String name, String valueDescription, AbstractFlagController<Double> controller, double defaultValue)
    {
        super(name, valueDescription, controller, DoubleSerialization.INSTANCE, defaultValue);
    }
}
