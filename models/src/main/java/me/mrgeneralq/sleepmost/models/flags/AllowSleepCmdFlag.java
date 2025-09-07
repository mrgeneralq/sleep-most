package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class AllowSleepCmdFlag extends BooleanFlag
{
	public AllowSleepCmdFlag(AbstractFlagController<Boolean> controller)
	{
		super("allow-sleep-cmd", controller, true);
	}
}
