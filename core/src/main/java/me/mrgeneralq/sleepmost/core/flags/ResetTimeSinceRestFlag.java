package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class ResetTimeSinceRestFlag extends BooleanFlag {

    public ResetTimeSinceRestFlag(AbstractFlagController<Boolean> controller) {
        super("reset-time-since-rest", controller, true);
    }
}
