package me.mrgeneralq.sleepmost.core.repositories;

import me.mrgeneralq.sleepmost.core.interfaces.IRepository;
import me.mrgeneralq.sleepmost.core.models.SleepMostWorld;

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
