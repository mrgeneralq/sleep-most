package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class ResetTimeSinceRestFlag extends BooleanFlag {

    public ResetTimeSinceRestFlag(AbstractFlagController<Boolean> controller) {
        super("reset-time-since-rest", controller, true);
    }
}
