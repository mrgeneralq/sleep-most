package me.qintinator.sleepmost.flags;

import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.interfaces.ISleepFlag;
import me.qintinator.sleepmost.interfaces.ISleepFlagService;
import me.qintinator.sleepmost.statics.Bootstrapper;
import org.bukkit.World;

public class PreventSleepFlag implements ISleepFlag<Boolean> {

    private final ISleepFlagService sleepFlagService;

    public PreventSleepFlag(){
        sleepFlagService = Bootstrapper.getBootstrapper().getSleepFlagService();
    }

    @Override
    public String getFlagName() {
        return "prevent-sleep";
    }

    @Override
    public String getFlagUsage() {
        return "/sleepmost setflag prevent-sleep <true|false>";
    }

    @Override
    public boolean isValidValue(String value) {
        return value.equals("true")||value.equals("false");
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.Boolean;
    }

    @Override
    public Boolean getValue(World world) {
        if(sleepFlagService.getFlag(world, getFlagName()) == null)
            return false;

        return (Boolean) sleepFlagService.getFlag(world, getFlagName());
    }

    @Override
    public void setValue(World world, Boolean value) {
        sleepFlagService.setFlag(world, getFlagName(), (Boolean) value);
    }


}
