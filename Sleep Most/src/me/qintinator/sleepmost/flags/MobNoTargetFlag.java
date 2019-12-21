package me.qintinator.sleepmost.flags;

import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.statics.Message;

public class MobNoTargetFlag implements me.qintinator.sleepmost.interfaces.ISleepFlag {
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
        return FlagType.Boolean;
    }
}
