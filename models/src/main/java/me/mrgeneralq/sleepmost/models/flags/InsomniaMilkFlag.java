package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class InsomniaMilkFlag extends BooleanFlag
{
	public InsomniaMilkFlag(AbstractFlagController<Boolean> controller)
	{
		super("insomnia-milk", controller, false);
	}
}
