package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class NightcycleAnimationFlag extends BooleanFlag
{
    public NightcycleAnimationFlag(AbstractFlagController<Boolean> controller) {
        super("nightcycle-animation", controller, false);
    }
}
