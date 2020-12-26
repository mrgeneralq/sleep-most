package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

public class PreventSleepFlag implements ISleepFlag<Boolean> {

    private final ISleepFlagService sleepFlagService;

    public PreventSleepFlag(ISleepFlagService sleepFlagService){
        this.sleepFlagService = sleepFlagService;
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
        return FlagType.BOOLEAN;
    }

    @Override
    public Boolean getValue(World world) {
        if(sleepFlagService.getFlagValue(world, getFlagName()) == null)
            return false;

        return (Boolean) sleepFlagService.getFlagValue(world, getFlagName());
    }

    @Override
    public void setValue(World world, Boolean value) {
        sleepFlagService.setFlag(world, getFlagName(), (Boolean) value);
    }


}
