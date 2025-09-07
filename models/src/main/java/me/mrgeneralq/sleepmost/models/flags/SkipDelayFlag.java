package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.IntegerFlag;

public class SkipDelayFlag extends IntegerFlag {
    public SkipDelayFlag(AbstractFlagController<Integer> controller) {
        super("skip-delay", "<delay in seconds>", controller, 0);
    }
}
