package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;

public class SkipStormSoundFlag extends SkipSoundFlag {

    public SkipStormSoundFlag(AbstractFlagController<String> controller) {
        super("skip-storm-sound", "entity.wither.spawn", controller);
    }
}