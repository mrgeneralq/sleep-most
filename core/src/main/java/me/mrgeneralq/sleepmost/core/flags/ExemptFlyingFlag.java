package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class ExemptFlyingFlag extends BooleanFlag {

    public ExemptFlyingFlag(AbstractFlagController<Boolean> controller)
    {
        super("exempt-flying", controller, false);
    }
}
