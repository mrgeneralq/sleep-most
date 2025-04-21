package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class ExemptSpectatorFlag extends BooleanFlag {

    public ExemptSpectatorFlag(AbstractFlagController<Boolean> controller)
    {
        super("exempt-spectator", controller, false);
    }
}
