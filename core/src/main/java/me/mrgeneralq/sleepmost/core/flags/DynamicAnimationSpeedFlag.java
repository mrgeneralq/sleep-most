package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class DynamicAnimationSpeedFlag extends BooleanFlag
{
	public DynamicAnimationSpeedFlag(AbstractFlagController<Boolean> controller)
	{
		super("dynamic-animation-speed", controller, false);
	}
}
