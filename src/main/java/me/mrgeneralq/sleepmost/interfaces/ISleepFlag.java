package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.enums.FlagType;
import org.bukkit.World;

public interface ISleepFlag<T> {

    public String getFlagName();
    public String getFlagUsage();
    public boolean isValidValue(String value);
    public FlagType getFlagType();
    public T getValue(World world);
    public void setValue(World world, T value);
    public void setValue(T value);
}
