package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class ExemptSpectatorFlag extends BooleanFlag {

    public ExemptSpectatorFlag(AbstractFlagController<Boolean> controller)
    {
        super("exempt-spectator", controller, false);
    }
}
