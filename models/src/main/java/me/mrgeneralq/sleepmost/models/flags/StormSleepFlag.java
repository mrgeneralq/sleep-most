package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class StormSleepFlag extends BooleanFlag
{
    public StormSleepFlag(AbstractFlagController<Boolean> controller)
    {
        super("storm-sleep", controller, true);
    }
}
