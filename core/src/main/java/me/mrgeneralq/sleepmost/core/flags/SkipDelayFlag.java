package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.IntegerFlag;

public class SkipDelayFlag extends IntegerFlag {
    public SkipDelayFlag(AbstractFlagController<Integer> controller) {
        super("skip-delay", "<delay in seconds>", controller, 0);
    }
}
