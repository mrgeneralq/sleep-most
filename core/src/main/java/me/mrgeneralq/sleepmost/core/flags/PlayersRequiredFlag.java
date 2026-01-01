package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.IntegerFlag;

public class PlayersRequiredFlag extends IntegerFlag
{
    public PlayersRequiredFlag(AbstractFlagController<Integer> controller)
    {
        super("players-required", "<players amount>", controller, 5);
    }
}
