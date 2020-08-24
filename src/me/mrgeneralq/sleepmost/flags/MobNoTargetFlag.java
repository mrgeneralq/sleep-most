package me.mrgeneralq.sleepmost.flags;
import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

public class MobNoTargetFlag implements ISleepFlag<Boolean> {


    private ISleepFlagService sleepFlagService;

    public MobNoTargetFlag(ISleepFlagService sleepFlagService){
        this.sleepFlagService = sleepFlagService;
    }


    @Override
    public String getFlagName() {
        return "mob-no-target";
    }

    @Override
    public String getFlagUsage() {
        return "/sleepmost setflag mob-no-target <true|false>";
    }

    @Override
    public boolean isValidValue(String value) {
        return value.equals("true")||value.equals("false");
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.BOOLEAN;
    }

    @Override
    public Boolean getValue(World world) {
        if(sleepFlagService.getFlagValue(world, this.getFlagName()) == null)
            return false;

        return (boolean) sleepFlagService.getFlagValue(world, this.getFlagName());
    }

    @Override
    public void setValue(World world, Boolean value) {

    }

}
