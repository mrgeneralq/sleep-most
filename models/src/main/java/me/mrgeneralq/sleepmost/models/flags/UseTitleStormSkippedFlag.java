package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class UseTitleStormSkippedFlag extends BooleanFlag
{
    public UseTitleStormSkippedFlag(AbstractFlagController<Boolean> controller) {
        super("use-title-storm-skipped", controller, false);
    }
}
