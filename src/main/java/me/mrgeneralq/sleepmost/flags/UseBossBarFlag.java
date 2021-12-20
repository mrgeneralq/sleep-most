package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseBossBarFlag extends BooleanFlag
{
    public UseBossBarFlag(AbstractFlagController<Boolean> controller) {
        super("use-bossbar", controller, false);
    }
}
