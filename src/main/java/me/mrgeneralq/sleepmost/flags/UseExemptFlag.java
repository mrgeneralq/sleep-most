package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseExemptFlag extends BooleanFlag
{
	public UseExemptFlag(AbstractFlagController<Boolean> controller)
	{
		super("use-exempt", controller);
	}
}
