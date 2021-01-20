package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class FeedFlag extends BooleanFlag {
    public FeedFlag(AbstractFlagController<Boolean> controller) {
        super("feed" , controller, false);
    }
}
