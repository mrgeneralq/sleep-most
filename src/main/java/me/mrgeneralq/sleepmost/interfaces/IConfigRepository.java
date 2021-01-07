package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import org.bukkit.World;

public interface IConfigRepository {

    double getPercentageRequired(World world);
    boolean containsWorld(World world);
    String getString(String string);
    int getCooldown();
    boolean getMobNoTarget(World world);
    boolean getUseExempt(World world);
    String getPrefix();
    void reloadConfig();
    void addWorld(World world);
    void removeWorld(World world);
    void disableForWorld(World world);

    <V> void setFlagValue(World world, ISleepFlag<V> flag, V value);
    String getFlagValue(World world, ISleepFlag<?> flag);
}
