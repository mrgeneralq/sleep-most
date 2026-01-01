package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class NonSleepingClockAnimationFlag extends BooleanFlag
{
    public NonSleepingClockAnimationFlag(AbstractFlagController<Boolean> controller) {
        super("non-sleeping-clock-animation", controller, false);
    }
}
