package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class AllowSleepCmdFlag extends BooleanFlag
{
	public AllowSleepCmdFlag(AbstractFlagController<Boolean> controller)
	{
		super("allow-sleep-cmd", controller, true);
	}
}
