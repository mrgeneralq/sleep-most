package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class NonSleepingSoundFlag extends BooleanFlag
{
    public NonSleepingSoundFlag(AbstractFlagController<Boolean> controller) {
        super("non-sleeping-sound", controller, false);
    }
}
