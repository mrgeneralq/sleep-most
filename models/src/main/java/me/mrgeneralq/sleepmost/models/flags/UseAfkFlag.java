package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class UseAfkFlag extends BooleanFlag
{
    public UseAfkFlag(AbstractFlagController<Boolean> controller) {
        super("use-afk", controller, false);
    }
}
