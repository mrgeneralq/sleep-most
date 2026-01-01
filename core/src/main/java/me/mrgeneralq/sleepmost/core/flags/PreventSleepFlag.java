package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class PreventSleepFlag extends BooleanFlag
{
    public PreventSleepFlag(AbstractFlagController<Boolean> controller)
    {
       super("prevent-sleep", controller, false);
    }
}
