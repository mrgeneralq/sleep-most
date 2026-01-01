package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class GsitSleepCmdFlag extends BooleanFlag
{
	public GsitSleepCmdFlag(AbstractFlagController<Boolean> controller)
	{
		super("gsit-sleep-cmd", controller, false);
	}
}
