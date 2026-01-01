package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class HealFlag extends BooleanFlag {
    public HealFlag(AbstractFlagController<Boolean> controller) {
        super("heal" , controller, false);
    }
}
