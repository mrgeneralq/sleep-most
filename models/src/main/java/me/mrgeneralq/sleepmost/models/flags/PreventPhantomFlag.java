package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class PreventPhantomFlag extends BooleanFlag
{
    public PreventPhantomFlag(AbstractFlagController<Boolean> controller)
    {
        super("prevent-phantom", controller, false);
    }
}
