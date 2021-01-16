package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseAfkFlag extends BooleanFlag
{
    public UseAfkFlag(AbstractFlagController<Boolean> controller)
    {
        super("use-afk", controller);
    }
}
