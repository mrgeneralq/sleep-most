package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.interfaces.IRepository;
import me.mrgeneralq.sleepmost.models.SleepMostPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Override
    public void remove(UUID playerId) {
        this.playerMap.remove(playerId);
    }

}
