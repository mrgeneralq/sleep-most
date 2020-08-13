package me.qintinator.sleepmost.flags;

import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.interfaces.IConfigRepository;
import me.qintinator.sleepmost.interfaces.ISleepFlag;
import me.qintinator.sleepmost.interfaces.ISleepFlagService;
import me.qintinator.sleepmost.statics.Bootstrapper;
import org.bukkit.World;

public class PercentageRequiredFlag implements ISleepFlag<Double> {


    private final IConfigRepository configRepository;
    private final Bootstrapper bootstrapper;
    private final ISleepFlagService sleepFlagService;

    public PercentageRequiredFlag(){
        configRepository = Bootstrapper.getBootstrapper().getConfigRepository();
        bootstrapper = Bootstrapper.getBootstrapper();
        this.sleepFlagService = bootstrapper.getSleepFlagService();
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
            double d = Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.Double;
    }

    @Override
    public Double getValue(World world) {
        if(sleepFlagService.getFlag(world, this.getFlagName()) == null)
            return null;
        return (Double) sleepFlagService.getFlag(world, this.getFlagName());
    }

    @Override
    public void setValue(World world, Double value) {

    }
}
