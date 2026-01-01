package me.mrgeneralq.sleepmost.core.flags.hooks.gsit;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class GSitSleepFlag extends BooleanFlag
{
	public GSitSleepFlag(AbstractFlagController<Boolean> controller)
	{
		super("gsit-sleep", controller, true);
	}
}
