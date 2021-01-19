package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.IntegerFlag;

public class PlayersRequiredFlag extends IntegerFlag
{
    public PlayersRequiredFlag(AbstractFlagController<Integer> controller)
    {
        super("players-required", "<players amount>", controller, 1);
    }
}
