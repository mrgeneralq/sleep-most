package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;

public class SkipNightSoundFlag extends SkipSoundFlag {

    public SkipNightSoundFlag(AbstractFlagController<String> controller) {
        super("skip-night-sound", "ui.toast.challenge_complete", controller);
    }
}
