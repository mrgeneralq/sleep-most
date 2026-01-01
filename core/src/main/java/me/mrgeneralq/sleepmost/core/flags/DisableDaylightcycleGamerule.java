package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class DisableDaylightcycleGamerule extends BooleanFlag {

    public DisableDaylightcycleGamerule(AbstractFlagController<Boolean> controller)
    {
        super("disable-daylight-cycle-gamerule", controller, true);
    }
}
