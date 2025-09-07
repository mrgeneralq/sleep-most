package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class NonSleepingClockAnimationFlag extends BooleanFlag
{
    public NonSleepingClockAnimationFlag(AbstractFlagController<Boolean> controller) {
        super("non-sleeping-clock-animation", controller, false);
    }
}
