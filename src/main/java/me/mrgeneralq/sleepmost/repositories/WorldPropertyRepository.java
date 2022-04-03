package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.interfaces.IRepository;
import me.mrgeneralq.sleepmost.models.WorldProperty;
import org.bukkit.World;

import java.util.HashMap;
import java.util.UUID;

public class WorldPropertyRepository implements IRepository<World, WorldProperty> {

    private final HashMap<UUID, WorldProperty> worldProperties = new HashMap<>();

    @Override
    public WorldProperty get(World world) {
        return this.worldProperties.get(world.getUID());
    }

    @Override
    public void set(World world, WorldProperty worldProperty) {
        this.worldProperties.put(world.getUID(), worldProperty);
    }

    @Override
    public boolean exists(World world) {
        return this.worldProperties.containsKey(world.getUID());
    }

    @Override
    public void remove(World world) {
        this.worldProperties.remove(world.getUID());
    }
}
