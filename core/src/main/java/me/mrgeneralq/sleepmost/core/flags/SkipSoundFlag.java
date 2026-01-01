package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.SoundResourceFlag;

public abstract class SkipSoundFlag extends SoundResourceFlag {

    public SkipSoundFlag(String name, String defaultValue, AbstractFlagController<String> controller) {
        super(name, defaultValue, controller);
    }
}
