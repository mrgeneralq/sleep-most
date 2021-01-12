package me.mrgeneralq.sleepmost.flags;
import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.BooleanFlag;

public class MobNoTargetFlag extends BooleanFlag
{
    public MobNoTargetFlag(AbstractFlagController<Boolean> controller)
    {
       super("mob-no-target", controller);
    }
}
