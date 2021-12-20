package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseSoundStormSkippedFlag extends BooleanFlag
{
    public UseSoundStormSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-sound-storm-skipped", controller, false);
    }
}
