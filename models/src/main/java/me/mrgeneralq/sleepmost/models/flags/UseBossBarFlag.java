package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class UseBossBarFlag extends BooleanFlag
{
    public UseBossBarFlag(AbstractFlagController<Boolean> controller) {
        super("use-bossbar", controller, false);
    }
}
