package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class InsomniaMilkFlag extends BooleanFlag
{
	public InsomniaMilkFlag(AbstractFlagController<Boolean> controller)
	{
		super("insomnia-milk", controller, false);
	}
}
