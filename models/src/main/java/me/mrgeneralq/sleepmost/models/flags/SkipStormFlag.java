package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class SkipStormFlag extends BooleanFlag
{
    public SkipStormFlag(AbstractFlagController<Boolean> controller)
    {
        super("skip-storm", controller, true);
    }
}
