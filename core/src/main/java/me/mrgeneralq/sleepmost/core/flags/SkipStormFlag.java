package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class SkipStormFlag extends BooleanFlag
{
    public SkipStormFlag(AbstractFlagController<Boolean> controller)
    {
        super("skip-storm", controller, true);
    }
}
