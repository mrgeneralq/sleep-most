package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.IntegerFlag;

public class PlayersRequiredFlag extends IntegerFlag
{
    public PlayersRequiredFlag(AbstractFlagController<Integer> controller)
    {
        super("players-required", "<players amount>", controller, 5);
    }
}
