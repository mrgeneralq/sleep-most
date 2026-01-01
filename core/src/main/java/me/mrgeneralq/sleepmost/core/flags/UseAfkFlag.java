package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class UseAfkFlag extends BooleanFlag
{
    public UseAfkFlag(AbstractFlagController<Boolean> controller) {
        super("use-afk", controller, false);
    }
}
