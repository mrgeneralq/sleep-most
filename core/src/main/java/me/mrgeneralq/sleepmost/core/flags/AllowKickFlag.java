package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class AllowKickFlag extends BooleanFlag
{
	public AllowKickFlag(AbstractFlagController<Boolean> controller)
	{
		super("allow-kick", controller, false);
	}
}
