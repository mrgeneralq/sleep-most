package me.mrgeneralq.sleepmost.repositories.concretes;

import me.mrgeneralq.sleepmost.interfaces.IRepository;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SleepMostWorldRepository implements IRepository<UUID, SleepMostWorld> {

    Map<UUID, SleepMostWorld> map = new HashMap<>();

    @Override
    public SleepMostWorld get(UUID worldUUID) {
        return this.map.get(worldUUID);
    }

    @Override
    public void set(UUID worldUUID, SleepMostWorld world) {
        this.map.put(worldUUID, world);
    }

    @Override
    public boolean exists(UUID worldUUID) {
        return this.map.containsKey(worldUUID);
    }

    @Override
    public void remove(UUID worldUUID) {
        this.map.remove(worldUUID);
    }

}
