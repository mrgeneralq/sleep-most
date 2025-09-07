package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.BooleanFlag;

public class MobNoTargetFlag extends BooleanFlag
{
    public MobNoTargetFlag(AbstractFlagController<Boolean> controller) {
       super("mob-no-target", controller, true);
    }
}
