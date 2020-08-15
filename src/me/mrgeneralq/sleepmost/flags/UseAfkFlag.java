package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import me.mrgeneralq.sleepmost.statics.Bootstrapper;
import org.bukkit.World;

public class UseAfkFlag implements ISleepFlag<Boolean> {

    private final Bootstrapper bootstrapper;
    private final ISleepFlagService sleepFlagService;

    public UseAfkFlag(){
        bootstrapper = Bootstrapper.getBootstrapper();
        sleepFlagService = bootstrapper.getSleepFlagService();


    }

    @Override
    public String getFlagName() {
        return "use-afk";
    }

    @Override
    public String getFlagUsage() {
        return "/sleepmost setflag use-afk <true|false>";
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
        if(sleepFlagService.getFlag(world, getFlagName()) == null)
            return null;

        return (Boolean) sleepFlagService.getFlag(world, getFlagName());
    }

    @Override
    public void setValue(World world, Boolean value) {
    }

}
