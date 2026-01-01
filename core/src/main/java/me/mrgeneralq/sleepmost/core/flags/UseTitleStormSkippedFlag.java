package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class UseTitleStormSkippedFlag extends BooleanFlag
{
    public UseTitleStormSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-title-storm-skipped", controller, false);
    }
}
