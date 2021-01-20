package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class PreventSleepFlag extends BooleanFlag
{
    public PreventSleepFlag(AbstractFlagController<Boolean> controller)
    {
       super("prevent-sleep", controller, false);
    }
}
