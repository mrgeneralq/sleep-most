package me.mrgeneralq.sleepmost.interfaces;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import org.bukkit.World;

public interface ISleepService {

    public boolean enabledForWorld(World world);
    public boolean sleepPercentageReached(World world);
    public double getPercentageRequired(World world);
    public boolean getMobNoTarget(World world);
    public double getSleepingPlayerPercentage(World world);
    public int getPlayersSleepingCount(World world);
    public int getRequiredPlayersSleepingCount(World world);
    public int getPlayerCountInWorld(World world);
    public void resetDay(World world, String lastSleeperName);
    public boolean resetRequired(World world);
    public boolean isNight(World world);
    public SleepSkipCause getSleepSkipCause(World world);
    public void reloadConfig();
    public void enableForWorld(World world);
    public void disableForWorld(World world);
    public void setFlag(World world, ISleepFlag<?> flag, String value);
}
