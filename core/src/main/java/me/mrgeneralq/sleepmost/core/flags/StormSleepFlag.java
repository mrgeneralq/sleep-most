package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class StormSleepFlag extends BooleanFlag
{
    public StormSleepFlag(AbstractFlagController<Boolean> controller)
    {
        super("storm-sleep", controller, true);
    }
}
