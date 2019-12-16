package me.qintinator.sleepmost.interfaces;
import me.qintinator.sleepmost.enums.SleepSkipCause;
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
    public void resetDay(World world);
    public boolean resetRequired(World world);
    public boolean isNight(World world);
    public SleepSkipCause getSleepSkipCause(World world);
}
