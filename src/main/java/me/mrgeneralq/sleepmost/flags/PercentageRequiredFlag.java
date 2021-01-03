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
    public String getName() {
       return "percentage-required";
    }

    @Override
    public String getUsage() {
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
    public FlagType getType() {
        return FlagType.DOUBLE;
    }

    @Override
    public Double getValue(World world) {
        if(sleepFlagService.getFlagValue(world, this.getName()) == null)
            return null;
        return (Double) sleepFlagService.getFlagValue(world, this.getName());
    }

    @Override
    public void setValue(World world, Double value) {

    }
}
