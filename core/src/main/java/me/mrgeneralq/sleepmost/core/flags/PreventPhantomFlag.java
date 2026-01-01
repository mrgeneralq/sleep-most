package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class PreventPhantomFlag extends BooleanFlag
{
    public PreventPhantomFlag(AbstractFlagController<Boolean> controller)
    {
        super("prevent-phantom", controller, false);
    }
}
