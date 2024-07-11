package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class GsitSleepCmdFlag extends BooleanFlag
{
	public GsitSleepCmdFlag(AbstractFlagController<Boolean> controller)
	{
		super("gsit-sleep-cmd", controller, false);
	}
}
