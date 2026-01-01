package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class UseTitleNightSkippedFlag extends BooleanFlag
{
    public UseTitleNightSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-title-night-skipped", controller, false);
    }
}
