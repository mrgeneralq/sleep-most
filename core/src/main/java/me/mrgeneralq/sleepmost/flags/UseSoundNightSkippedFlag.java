package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseSoundNightSkippedFlag extends UseSkipSoundFlag {

    public UseSoundNightSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-sound-night-skipped", false, controller);
    }
}
