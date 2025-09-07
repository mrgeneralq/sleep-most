package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class ResetTimeSinceRestFlag extends BooleanFlag {

    public ResetTimeSinceRestFlag(AbstractFlagController<Boolean> controller) {
        super("reset-time-since-rest", controller, true);
    }
}
