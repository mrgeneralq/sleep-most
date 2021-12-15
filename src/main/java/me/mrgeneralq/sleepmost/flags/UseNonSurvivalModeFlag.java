package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class UseNonSurvivalModeFlag extends BooleanFlag {

    public UseNonSurvivalModeFlag(AbstractFlagController<Boolean> controller)
    {
        super("use-non-survival-mode", controller, false);
    }
}
