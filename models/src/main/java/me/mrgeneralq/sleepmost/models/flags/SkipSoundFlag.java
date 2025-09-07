package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.SoundResourceFlag;

public abstract class SkipSoundFlag extends SoundResourceFlag {

    public SkipSoundFlag(String name, String defaultValue, AbstractFlagController<String> controller) {
        super(name, defaultValue, controller);
    }
}
