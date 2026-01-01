package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.IntegerFlag;

public class ExemptBelowYFlag extends IntegerFlag
{
    public ExemptBelowYFlag(AbstractFlagController<Integer> controller)
    {
        super("exempt-below-y", "<Y axis>", controller, -1);
    }
}
