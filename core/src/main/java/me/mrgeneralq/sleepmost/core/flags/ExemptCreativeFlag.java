package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class ExemptCreativeFlag extends BooleanFlag {

    public ExemptCreativeFlag(AbstractFlagController<Boolean> controller) {
        super("exempt-creative", controller, false);
    }
}
