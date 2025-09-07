package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class UseTitleNightSkippedFlag extends BooleanFlag
{
    public UseTitleNightSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-title-night-skipped", controller, false);
    }
}
