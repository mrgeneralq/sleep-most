package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;

public class UseSoundStormSkippedFlag extends UseSkipSoundFlag {

    public UseSoundStormSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-sound-storm-skipped", false, controller);
    }
}
