package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.IntegerFlag;

public class ExemptBelowYFlag extends IntegerFlag
{
    public ExemptBelowYFlag(AbstractFlagController<Integer> controller)
    {
        super("exempt-below-y", "<Y axis>", controller, -1);
    }
}
