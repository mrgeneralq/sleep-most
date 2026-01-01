package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class NonSleepingTitleFlag extends BooleanFlag
{
    public NonSleepingTitleFlag(AbstractFlagController<Boolean> controller) {
        super("non-sleeping-title", controller, false);
    }
}
