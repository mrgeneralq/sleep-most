package me.mrgeneralq.sleepmost.core.interfaces;

import me.mrgeneralq.sleepmost.core.models.SleepMostPlayer;
import org.bukkit.OfflinePlayer;

import java.util.List;


public interface ISleepMostPlayerService {

    SleepMostPlayer getPlayer(OfflinePlayer playerId);
    void updatePlayer(SleepMostPlayer player);
    void unregisterPlayer(OfflinePlayer playerId);
    void registerNewPlayer(OfflinePlayer offlinePlayer);

    boolean playerExists(OfflinePlayer offlinePlayer);

    List<SleepMostPlayer> getAllPlayers();
}
