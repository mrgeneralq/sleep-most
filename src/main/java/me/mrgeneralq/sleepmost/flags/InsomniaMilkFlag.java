package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class InsomniaMilkFlag extends BooleanFlag
{
	public InsomniaMilkFlag(AbstractFlagController<Boolean> controller)
	{
		super("insomnia-milk", controller, false);
	}
}
