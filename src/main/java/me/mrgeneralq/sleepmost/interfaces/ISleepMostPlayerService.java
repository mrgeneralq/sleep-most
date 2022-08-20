package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.models.SleepMostPlayer;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public interface ISleepMostPlayerService {

    SleepMostPlayer getPlayer(OfflinePlayer playerId);
    void updatePlayer(SleepMostPlayer player);
    void unregisterPlayer(OfflinePlayer playerId);
    void registerNewPlayer(OfflinePlayer offlinePlayer);

}
