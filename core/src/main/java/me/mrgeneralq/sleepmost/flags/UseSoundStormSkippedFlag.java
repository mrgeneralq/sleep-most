package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseSoundStormSkippedFlag extends UseSkipSoundFlag {

    public UseSoundStormSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-sound-storm-skipped", false, controller);
    }
}
