package me.mrgeneralq.sleepmost.interfaces;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import org.bukkit.World;

public interface ISleepService {

    boolean enabledForWorld(World world);
    boolean sleepPercentageReached(World world);
    double getPercentageRequired(World world);
    boolean getMobNoTarget(World world);
    double getSleepingPlayerPercentage(World world);
    int getPlayersSleepingCount(World world);
    int getRequiredPlayersSleepingCount(World world);
    int getPlayerCountInWorld(World world);
    void resetDay(World world, String lastSleeperName, String lastSleeperDisplayName);
    boolean resetRequired(World world);
    boolean isNight(World world);
    SleepSkipCause getSleepSkipCause(World world);
    void reloadConfig();
    void enableForWorld(World world);
    void disableForWorld(World world);
}
