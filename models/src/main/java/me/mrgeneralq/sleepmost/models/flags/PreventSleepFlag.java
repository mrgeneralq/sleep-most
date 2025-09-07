package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class PreventSleepFlag extends BooleanFlag
{
    public PreventSleepFlag(AbstractFlagController<Boolean> controller)
    {
       super("prevent-sleep", controller, false);
    }
}
