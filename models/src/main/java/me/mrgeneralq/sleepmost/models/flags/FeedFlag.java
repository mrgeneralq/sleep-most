package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class FeedFlag extends BooleanFlag {
    public FeedFlag(AbstractFlagController<Boolean> controller) {
        super("feed" , controller, false);
    }
}
