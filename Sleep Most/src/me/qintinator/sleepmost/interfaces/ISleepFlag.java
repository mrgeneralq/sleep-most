package me.qintinator.sleepmost.interfaces;

import me.qintinator.sleepmost.enums.FlagType;

public interface ISleepFlag {

    public String getFlagName();
    public String getFlagUsage();
    public boolean isValidValue(String value);
    public FlagType getFlagType();
}
