package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class AllowKickFlag extends BooleanFlag
{
	public AllowKickFlag(AbstractFlagController<Boolean> controller)
	{
		super("allow-kick", controller, false);
	}
}
