package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;

public class SkipNightSoundFlag extends SkipSoundFlag {

    public SkipNightSoundFlag(AbstractFlagController<String> controller) {
        super("skip-night-sound", "ui.toast.challenge_complete", controller);
    }
}
