package me.mrgeneralq.sleepmost.flags.gsit;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class GSitSleepFlag extends BooleanFlag
{
	public GSitSleepFlag(AbstractFlagController<Boolean> controller)
	{
		super("gsit-sleep", controller, true);
	}
}
