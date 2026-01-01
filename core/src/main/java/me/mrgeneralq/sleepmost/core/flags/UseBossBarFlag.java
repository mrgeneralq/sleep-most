package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class UseBossBarFlag extends BooleanFlag
{
    public UseBossBarFlag(AbstractFlagController<Boolean> controller) {
        super("use-bossbar", controller, false);
    }
}
