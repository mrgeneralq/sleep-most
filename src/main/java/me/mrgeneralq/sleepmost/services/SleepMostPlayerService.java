package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.ISleepMostPlayerService;
import me.mrgeneralq.sleepmost.models.SleepMostPlayer;
import me.mrgeneralq.sleepmost.repositories.SleepMostPlayerRepository;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class SleepMostPlayerService implements ISleepMostPlayerService {

    SleepMostPlayerRepository repository;

    public SleepMostPlayerService(SleepMostPlayerRepository sleepMostPlayerRepository) {
        this.repository = sleepMostPlayerRepository;
    }

    @Override
    public SleepMostPlayer getPlayer(OfflinePlayer player) {
        return this.repository.get(player.getUniqueId());
    }

    @Override
    public void updatePlayer(SleepMostPlayer player) {
        this.repository.set(player.getPlayerUUID().getUniqueId(), player);
    }

    @Override
    public void unregisterPlayer(OfflinePlayer offlinePlayer) {
        this.repository.remove(offlinePlayer.getUniqueId());
    }

    @Override
    public void registerNewPlayer(OfflinePlayer offlinePlayer) {

        if(offlinePlayer == null || !offlinePlayer.isOnline()){
            //detect NPC
            return;
        }
        SleepMostPlayer player = new SleepMostPlayer(offlinePlayer);
        this.repository.set(offlinePlayer.getUniqueId(), player);
    }

    @Override
    public boolean playerExists(OfflinePlayer offlinePlayer){
        return this.repository.exists(offlinePlayer.getUniqueId());
    }

    @Override
    public List<SleepMostPlayer> getAllPlayers(){
        return this.repository.getAll();
    }
}
