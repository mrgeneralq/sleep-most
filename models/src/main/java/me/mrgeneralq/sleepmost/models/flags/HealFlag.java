package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class HealFlag extends BooleanFlag {
    public HealFlag(AbstractFlagController<Boolean> controller) {
        super("heal" , controller, false);
    }
}
