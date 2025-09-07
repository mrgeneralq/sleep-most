package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.IntegerFlag;

public class LongerNightDurationFlag extends IntegerFlag
{
    public LongerNightDurationFlag(AbstractFlagController<Integer> controller)
    {
        super("longer-night-duration", "<duration in seconds>", controller, 0);
    }
}
