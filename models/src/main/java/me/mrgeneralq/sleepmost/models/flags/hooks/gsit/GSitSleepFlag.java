package me.mrgeneralq.sleepmost.models.flags.hooks.gsit;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class GSitSleepFlag extends BooleanFlag
{
	public GSitSleepFlag(AbstractFlagController<Boolean> controller)
	{
		super("gsit-sleep", controller, true);
	}
}
