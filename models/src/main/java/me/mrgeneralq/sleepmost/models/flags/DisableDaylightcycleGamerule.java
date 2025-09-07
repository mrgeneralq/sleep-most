package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class DisableDaylightcycleGamerule extends BooleanFlag {

    public DisableDaylightcycleGamerule(AbstractFlagController<Boolean> controller)
    {
        super("disable-daylight-cycle-gamerule", controller, true);
    }
}
