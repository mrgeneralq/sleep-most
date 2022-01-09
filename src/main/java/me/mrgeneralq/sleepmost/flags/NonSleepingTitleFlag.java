package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class NonSleepingTitleFlag extends BooleanFlag
{
    public NonSleepingTitleFlag(AbstractFlagController<Boolean> controller) {
        super("non-sleeping-title", controller, false);
    }
}
