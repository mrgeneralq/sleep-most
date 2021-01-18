package me.mrgeneralq.sleepmost.interfaces;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface ISleepService
{
    //General
    void executeSleepReset(World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause skipCause);
    void reloadConfig();

    //Worlds
    boolean isEnabledAt(World world);
    void enableAt(World world);
    void disableAt(World world);
    int getSleepersAmount(World world);
    int getPlayerCountInWorld(World world);
    double getSleepersPercentage(World world);
    int getRequiredSleepersCount(World world);
    SleepSkipCause getCurrentSkipCause(World world);
    boolean isRequiredCountReached(World world);
    void clearSleepersAt(World world);

    //Players
    void setSleeping(Player player, boolean sleeping);
    boolean isPlayerAsleep(Player player);

    //Skip Status
    boolean resetRequired(World world);
    boolean isNight(World world);
}
