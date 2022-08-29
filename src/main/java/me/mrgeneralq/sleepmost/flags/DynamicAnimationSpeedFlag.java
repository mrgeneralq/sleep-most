package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class DynamicAnimationSpeedFlag extends BooleanFlag
{
	public DynamicAnimationSpeedFlag(AbstractFlagController<Boolean> controller)
	{
		super("dynamic-animation-speed", controller, false);
	}
}
