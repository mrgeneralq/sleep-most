package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class ClockAnimationFlag extends BooleanFlag
{
	public ClockAnimationFlag(AbstractFlagController<Boolean> controller)
	{
		super("clock-animation", controller, true);
	}
}
