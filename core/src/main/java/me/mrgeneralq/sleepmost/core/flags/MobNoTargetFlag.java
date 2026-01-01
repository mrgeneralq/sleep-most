package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.types.BooleanFlag;

public class MobNoTargetFlag extends BooleanFlag
{
    public MobNoTargetFlag(AbstractFlagController<Boolean> controller) {
       super("mob-no-target", controller, true);
    }
}
