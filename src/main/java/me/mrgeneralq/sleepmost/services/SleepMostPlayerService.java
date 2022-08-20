package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.ISleepMostPlayerService;
import me.mrgeneralq.sleepmost.models.SleepMostPlayer;
import me.mrgeneralq.sleepmost.repositories.SleepMostPlayerRepository;
import org.bukkit.OfflinePlayer;

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
        this.repository.set(player.getPlayer().getUniqueId(), player);
    }

    @Override
    public void unregisterPlayer(OfflinePlayer offlinePlayer) {
        this.repository.remove(offlinePlayer.getUniqueId());
    }

    @Override
    public void registerNewPlayer(OfflinePlayer offlinePlayer) {
        SleepMostPlayer player = new SleepMostPlayer(offlinePlayer);
        this.repository.set(offlinePlayer.getUniqueId(), player);
    }
}
