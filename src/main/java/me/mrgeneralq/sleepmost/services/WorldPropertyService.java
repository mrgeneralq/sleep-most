package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IWorldPropertyService;
import me.mrgeneralq.sleepmost.models.WorldProperty;
import me.mrgeneralq.sleepmost.repositories.WorldPropertyRepository;
import org.bukkit.World;

public class WorldPropertyService implements IWorldPropertyService{

    private final WorldPropertyRepository worldPropertyRepository;

    public WorldPropertyService(WorldPropertyRepository worldPropertyRepository) {
        this.worldPropertyRepository = worldPropertyRepository;
    }

    @Override
    public WorldProperty getWorldProperties(World world) {
        return this.worldPropertyRepository.get(world);
    }

    @Override
    public void createNewWorldProperty(World world) {
        WorldProperty property = WorldProperty.getNewProperty();
        this.worldPropertyRepository.set(world, property);
    }

    @Override
    public void setWorldProperty(World world, WorldProperty property) {
        this.worldPropertyRepository.set(world, property);
    }


    @Override
    public boolean propertyExists(World world){
        return this.worldPropertyRepository.exists(world);
    }
}
