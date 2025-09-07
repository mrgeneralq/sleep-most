package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class AllowKickFlag extends BooleanFlag
{
	public AllowKickFlag(AbstractFlagController<Boolean> controller)
	{
		super("allow-kick", controller, false);
	}
}
