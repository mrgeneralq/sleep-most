package me.mrgeneralq.sleepmost.interfaces;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface ISleepService
{
    //General
    //boolean canReset(World world);


    void reloadConfig();

    //Worlds
    boolean isEnabledAt(World world);
    void enableAt(World world);
    void disableAt(World world);

    boolean canReset(World world);
    int getSleepingPlayersAmount(World world);
    int getPlayerCountInWorld(World world);
    double getSleepingPlayerPercentage(World world);
    int getRequiredPlayersSleepingCount(World world);
    SleepSkipCause getCurrentSkipCause(World world);
    boolean isRequiredCountReached(World world);

    //Players
    void setSleeping(Player player, boolean sleeping);
    boolean isPlayerAsleep(Player player);

    //Skip Status
    boolean resetRequired(World world);
    boolean isNight(World world);

    void executeSleepReset(World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause skipCause);

    /*
    Removed methods
     */
    //boolean sleepPercentageReached(World world);
}
