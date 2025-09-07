package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class UseExemptFlag extends BooleanFlag
{
	public UseExemptFlag(AbstractFlagController<Boolean> controller)
	{
		super("use-exempt", controller, false);
	}
}
