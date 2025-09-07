package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class DynamicAnimationSpeedFlag extends BooleanFlag
{
	public DynamicAnimationSpeedFlag(AbstractFlagController<Boolean> controller)
	{
		super("dynamic-animation-speed", controller, false);
	}
}
