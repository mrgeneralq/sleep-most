package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class GsitSleepCmdFlag extends BooleanFlag
{
	public GsitSleepCmdFlag(AbstractFlagController<Boolean> controller)
	{
		super("gsit-sleep-cmd", controller, false);
	}
}
