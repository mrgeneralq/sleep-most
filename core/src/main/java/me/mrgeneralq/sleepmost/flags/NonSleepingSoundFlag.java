package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class NonSleepingSoundFlag extends BooleanFlag
{
    public NonSleepingSoundFlag(AbstractFlagController<Boolean> controller) {
        super("non-sleeping-sound", controller, false);
    }
}
