package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;

public class SkipStormSoundFlag extends SkipSoundFlag {

    public SkipStormSoundFlag(AbstractFlagController<String> controller) {
        super("skip-storm-sound", "entity.wither.spawn", controller);
    }
}