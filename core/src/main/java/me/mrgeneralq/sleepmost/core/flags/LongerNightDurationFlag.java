package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.IntegerFlag;

public class LongerNightDurationFlag extends IntegerFlag
{
    public LongerNightDurationFlag(AbstractFlagController<Integer> controller)
    {
        super("longer-night-duration", "<duration in seconds>", controller, 0);
    }
}
