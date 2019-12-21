package me.qintinator.sleepmost.flags;

import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.interfaces.ISleepFlag;

public class PercentageRequiredFlag implements ISleepFlag {
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
}
