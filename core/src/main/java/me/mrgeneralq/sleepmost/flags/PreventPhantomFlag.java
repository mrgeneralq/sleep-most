package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class PreventPhantomFlag extends BooleanFlag
{
    public PreventPhantomFlag(AbstractFlagController<Boolean> controller)
    {
        super("prevent-phantom", controller, false);
    }
}
