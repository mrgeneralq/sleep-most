package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class ExemptFlyingFlag extends BooleanFlag {

    public ExemptFlyingFlag(AbstractFlagController<Boolean> controller)
    {
        super("exempt-flying", controller, false);
    }
}
