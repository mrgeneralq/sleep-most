package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class ForceNightcycleAnimationFlag extends BooleanFlag {
    public ForceNightcycleAnimationFlag(AbstractFlagController<Boolean> controller) {
        super("force-nightcycle-animation" , controller, true);
    }
}
