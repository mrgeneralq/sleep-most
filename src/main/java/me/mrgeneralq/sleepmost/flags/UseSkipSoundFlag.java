package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseSkipSoundFlag extends BooleanFlag {

    public UseSkipSoundFlag(String flagKey, Boolean defaultValue, AbstractFlagController<Boolean> controller) {
        super(flagKey, controller, defaultValue);
    }
}
