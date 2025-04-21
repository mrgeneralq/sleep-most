package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.SoundResourceFlag;

public abstract class SkipSoundFlag extends SoundResourceFlag {

    public SkipSoundFlag(String name, String defaultValue, AbstractFlagController<String> controller) {
        super(name, defaultValue, controller);
    }
}
