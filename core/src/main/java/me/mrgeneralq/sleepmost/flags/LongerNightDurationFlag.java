package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.IntegerFlag;

public class LongerNightDurationFlag extends IntegerFlag
{
    public LongerNightDurationFlag(AbstractFlagController<Integer> controller)
    {
        super("longer-night-duration", "<duration in seconds>", controller, 0);
    }
}
