package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class SkipStormFlag extends BooleanFlag
{
    public SkipStormFlag(AbstractFlagController<Boolean> controller)
    {
        super("skip-storm", controller, true);
    }
}
