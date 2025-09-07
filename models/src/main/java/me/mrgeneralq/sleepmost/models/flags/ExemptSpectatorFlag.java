package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class ExemptSpectatorFlag extends BooleanFlag {

    public ExemptSpectatorFlag(AbstractFlagController<Boolean> controller)
    {
        super("exempt-spectator", controller, false);
    }
}
