package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class NightcycleAnimationFlag extends BooleanFlag
{
    public NightcycleAnimationFlag(AbstractFlagController<Boolean> controller) {
        super("nightcycle-animation", controller, false);
    }
}
