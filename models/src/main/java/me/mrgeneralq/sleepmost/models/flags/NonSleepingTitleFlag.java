package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class NonSleepingTitleFlag extends BooleanFlag
{
    public NonSleepingTitleFlag(AbstractFlagController<Boolean> controller) {
        super("non-sleeping-title", controller, false);
    }
}
