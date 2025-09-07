package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class ExemptFlyingFlag extends BooleanFlag {

    public ExemptFlyingFlag(AbstractFlagController<Boolean> controller)
    {
        super("exempt-flying", controller, false);
    }
}
