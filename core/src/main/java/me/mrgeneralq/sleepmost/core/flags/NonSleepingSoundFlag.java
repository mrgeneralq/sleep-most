package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class NonSleepingSoundFlag extends BooleanFlag
{
    public NonSleepingSoundFlag(AbstractFlagController<Boolean> controller) {
        super("non-sleeping-sound", controller, false);
    }
}
