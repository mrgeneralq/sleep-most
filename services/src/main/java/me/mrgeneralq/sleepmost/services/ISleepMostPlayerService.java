package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.models.SleepMostPlayer;
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
