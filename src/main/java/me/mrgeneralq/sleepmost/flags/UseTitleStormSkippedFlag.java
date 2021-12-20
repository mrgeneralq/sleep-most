package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseTitleStormSkippedFlag extends BooleanFlag
{
    public UseTitleStormSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-title-storm-skipped", controller, false);
    }
}
