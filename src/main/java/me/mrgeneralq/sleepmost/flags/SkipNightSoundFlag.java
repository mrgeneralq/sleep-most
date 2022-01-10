package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.SoundResourceFlag;

public class SkipNightSoundFlag extends SkipSoundFlag {

    public SkipNightSoundFlag(AbstractFlagController<String> controller) {
        super("skip-night-sound", "ui.toast.challenge_complete", controller);
    }
}
