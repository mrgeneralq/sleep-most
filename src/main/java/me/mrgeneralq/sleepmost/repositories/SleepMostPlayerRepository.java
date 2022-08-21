package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.interfaces.IRepository;
import me.mrgeneralq.sleepmost.models.SleepMostPlayer;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class SleepMostPlayerRepository implements IRepository<UUID, SleepMostPlayer> {

    Map<UUID, SleepMostPlayer> playerMap = new HashMap<>();

    @Override
    public SleepMostPlayer get(UUID playerId) {
        return this.playerMap.get(playerId);
    }

    @Override
    public void set(UUID playerId, SleepMostPlayer player) {
        this.playerMap.put(playerId,player);
    }

    @Override
    public boolean exists(UUID playerId) {
        return this.playerMap.containsKey(playerId);
    }

    public List<SleepMostPlayer> getAll(){
        return this.playerMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public void remove(UUID playerId) {
        this.playerMap.remove(playerId);
    }

}
