package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class HealFlag extends BooleanFlag {
    public HealFlag(AbstractFlagController<Boolean> controller) {
        super("heal" , controller);
    }
}
