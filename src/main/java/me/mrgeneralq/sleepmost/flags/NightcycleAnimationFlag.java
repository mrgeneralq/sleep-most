package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class NightcycleAnimationFlag extends BooleanFlag
{
    public NightcycleAnimationFlag(AbstractFlagController<Boolean> controller) {
        super("nightcycle-animation", controller, false);
    }
}
