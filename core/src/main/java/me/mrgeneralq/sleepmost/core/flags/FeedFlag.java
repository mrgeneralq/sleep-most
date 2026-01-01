package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class FeedFlag extends BooleanFlag {
    public FeedFlag(AbstractFlagController<Boolean> controller) {
        super("feed" , controller, false);
    }
}
