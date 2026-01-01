package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class AllowSleepCmdFlag extends BooleanFlag
{
	public AllowSleepCmdFlag(AbstractFlagController<Boolean> controller)
	{
		super("allow-sleep-cmd", controller, true);
	}
}
