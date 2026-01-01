package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;

public class UseSoundStormSkippedFlag extends UseSkipSoundFlag {

    public UseSoundStormSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-sound-storm-skipped", false, controller);
    }
}
