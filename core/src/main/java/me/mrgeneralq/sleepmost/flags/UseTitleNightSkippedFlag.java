package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseTitleNightSkippedFlag extends BooleanFlag
{
    public UseTitleNightSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-title-night-skipped", controller, false);
    }
}
