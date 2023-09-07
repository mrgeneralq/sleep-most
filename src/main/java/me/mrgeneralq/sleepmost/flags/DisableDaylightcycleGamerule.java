package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class DisableDaylightcycleGamerule extends BooleanFlag {

    public DisableDaylightcycleGamerule(AbstractFlagController<Boolean> controller)
    {
        super("disable-daylight-cycle-gamerule", controller, true);
    }
}
