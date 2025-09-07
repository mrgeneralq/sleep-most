package me.mrgeneralq.sleepmost.models.flags.hooks.gsit;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class GSitHookFlag extends BooleanFlag
{
	public GSitHookFlag(AbstractFlagController<Boolean> controller)
	{
		super("gsit-hook", controller, false);
	}
}
