package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.IntegerFlag;

public class SkipDelayFlag extends IntegerFlag {
    public SkipDelayFlag(AbstractFlagController<Integer> controller) {
        super("skip-delay", "<delay in seconds>", controller, 0);
    }
}
