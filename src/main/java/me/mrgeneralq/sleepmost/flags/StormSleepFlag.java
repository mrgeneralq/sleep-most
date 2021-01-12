package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class StormSleepFlag extends BooleanFlag
{
    public StormSleepFlag(AbstractFlagController<Boolean> controller)
    {
        super("storm-sleep", controller);
    }
}
