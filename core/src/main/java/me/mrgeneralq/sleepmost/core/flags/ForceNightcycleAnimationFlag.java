package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class ForceNightcycleAnimationFlag extends BooleanFlag {
    public ForceNightcycleAnimationFlag(AbstractFlagController<Boolean> controller) {
        super("force-nightcycle-animation" , controller, true);
    }
}
