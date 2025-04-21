package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class ForceNightcycleAnimationFlag extends BooleanFlag {
    public ForceNightcycleAnimationFlag(AbstractFlagController<Boolean> controller) {
        super("force-nightcycle-animation" , controller, true);
    }
}
