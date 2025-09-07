package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class ExemptCreativeFlag extends BooleanFlag {

    public ExemptCreativeFlag(AbstractFlagController<Boolean> controller) {
        super("exempt-creative", controller, false);
    }
}
