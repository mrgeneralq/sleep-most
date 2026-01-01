package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;

public class SkipStormSoundFlag extends SkipSoundFlag {

    public SkipStormSoundFlag(AbstractFlagController<String> controller) {
        super("skip-storm-sound", "entity.wither.spawn", controller);
    }
}