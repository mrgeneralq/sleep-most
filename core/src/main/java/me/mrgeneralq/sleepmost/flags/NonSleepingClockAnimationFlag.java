package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class NonSleepingClockAnimationFlag extends BooleanFlag
{
    public NonSleepingClockAnimationFlag(AbstractFlagController<Boolean> controller) {
        super("non-sleeping-clock-animation", controller, false);
    }
}
