package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

public class PercentageRequiredFlag implements ISleepFlag<Double> {


    private final ISleepFlagService sleepFlagService;

    public PercentageRequiredFlag(ISleepFlagService sleepFlagService){
        this.sleepFlagService = sleepFlagService;
    }

    @Override
    public String getFlagName() {
       return "percentage-required";
    }

    @Override
    public String getFlagUsage() {
        return "Use /sleepmost setflag percentage-required <0.1 - 1>";
    }

    @Override
    public boolean isValidValue(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.DOUBLE;
    }

    @Override
    public Double getValue(World world) {
        if(sleepFlagService.getFlagValue(world, this.getFlagName()) == null)
            return null;
        return (Double) sleepFlagService.getFlagValue(world, this.getFlagName());
    }

    @Override
    public void setValue(World world, Double value) {

    }
}
