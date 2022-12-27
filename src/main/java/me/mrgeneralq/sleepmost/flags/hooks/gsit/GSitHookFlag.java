package me.mrgeneralq.sleepmost.flags.hooks.gsit;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class GSitHookFlag extends BooleanFlag
{
	public GSitHookFlag(AbstractFlagController<Boolean> controller)
	{
		super("gsit-hook", controller, false);
	}
}
