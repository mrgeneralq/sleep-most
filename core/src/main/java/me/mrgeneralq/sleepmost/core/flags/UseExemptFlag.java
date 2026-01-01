package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class UseExemptFlag extends BooleanFlag
{
	public UseExemptFlag(AbstractFlagController<Boolean> controller)
	{
		super("use-exempt", controller, false);
	}
}
