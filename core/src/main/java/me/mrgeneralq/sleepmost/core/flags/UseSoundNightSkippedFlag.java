package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;

public class UseSoundNightSkippedFlag extends UseSkipSoundFlag {

    public UseSoundNightSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-sound-night-skipped", false, controller);
    }
}
