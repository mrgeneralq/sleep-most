package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;

public class UseSoundNightSkippedFlag extends UseSkipSoundFlag {

    public UseSoundNightSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-sound-night-skipped", false, controller);
    }
}
