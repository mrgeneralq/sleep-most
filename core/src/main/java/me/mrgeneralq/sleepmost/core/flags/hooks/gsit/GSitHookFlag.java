package me.mrgeneralq.sleepmost.core.flags.hooks.gsit;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class GSitHookFlag extends BooleanFlag
{
	public GSitHookFlag(AbstractFlagController<Boolean> controller)
	{
		super("gsit-hook", controller, false);
	}
}
