package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

public class PreventPhantomFlag implements ISleepFlag<Boolean> {

    private final ISleepFlagService sleepFlagService;

    public PreventPhantomFlag(ISleepFlagService sleepFlagService){
        this.sleepFlagService = sleepFlagService;
    }

    @Override
    public String getFlagName() {
        return "prevent-phantom";
    }

    @Override
    public String getFlagUsage() {
        return "/sleepmost setflag prevent-phantom <true|false>";
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

        return (boolean) sleepFlagService.getFlagValue(world, getFlagName());
    }

    @Override
    public void setValue(World world, Boolean value) {
        sleepFlagService.setFlag(world, getFlagName(), (Boolean) value);
    }


}
