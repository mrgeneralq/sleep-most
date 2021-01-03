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
    public String getName() {
        return "mob-no-target";
    }

    @Override
    public String getUsage() {
        return "/sleepmost setflag mob-no-target <true|false>";
    }

    @Override
    public boolean isValidValue(String value) {
        return value.equals("true")||value.equals("false");
    }

    @Override
    public FlagType getType() {
        return FlagType.BOOLEAN;
    }

    @Override
    public Boolean getValue(World world) {
        if(sleepFlagService.getFlagValue(world, this.getName()) == null)
            return false;

        return (boolean) sleepFlagService.getFlagValue(world, this.getName());
    }

    @Override
    public void setValue(World world, Boolean value) {

    }

}
