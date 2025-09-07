package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class ClockAnimationFlag extends BooleanFlag
{
	public ClockAnimationFlag(AbstractFlagController<Boolean> controller)
	{
		super("clock-animation", controller, true);
	}
}
