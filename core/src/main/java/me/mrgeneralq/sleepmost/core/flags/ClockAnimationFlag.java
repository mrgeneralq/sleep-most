package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class ClockAnimationFlag extends BooleanFlag
{
	public ClockAnimationFlag(AbstractFlagController<Boolean> controller)
	{
		super("clock-animation", controller, true);
	}
}
