package me.mrgeneralq.sleepmost.interfaces;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public interface ISleepService
{
    //General
    void executeSleepReset(World world, String lastSleeperName, String lastSleeperDisplayName, List<OfflinePlayer> peopleWhoSlept , SleepSkipCause skipCause);
    void reloadConfig();

    //Worlds
    boolean isEnabledAt(World world);
    void enableAt(World world);
    void disableAt(World world);
    int getSleepersAmount(World world);

    List<Player> getSleepers(World world);

    int getPlayerCountInWorld(World world);
    double getSleepersPercentage(World world);
    int getRequiredSleepersCount(World world);
    SleepSkipCause getCurrentSkipCause(World world);
    boolean isRequiredCountReached(World world);

    int getRemainingSleepers(World world);

    void clearSleepersAt(World world);

    //Players
    void setSleeping(Player player, boolean sleeping);
    boolean isPlayerAsleep(Player player);

    //Skip Status
    boolean isSleepingPossible(World world);
    boolean isNight(World world);

    boolean shouldSkip(World world);

    void runSkipAnimation(Player player, SleepSkipCause sleepSkipCause);
}
