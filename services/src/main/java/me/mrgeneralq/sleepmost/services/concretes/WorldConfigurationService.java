package me.mrgeneralq.sleepmost.services.concretes;

import me.mrgeneralq.sleepmost.models.WorldConfiguration;
import me.mrgeneralq.sleepmost.services.IWorldConfigurationService;
import org.bukkit.World;

public class WorldConfigurationService implements IWorldConfigurationService {
    @Override
    public WorldConfiguration createConfiguration(String worldName) {

        WorldConfiguration worldConfiguration = new WorldConfiguration(worldName);
        return worldConfiguration;
    }

    @Override
    public WorldConfiguration getConfiguration(String worldName) {
        return null;
    }

    @Override
    public void updateConfiguration(WorldConfiguration worldConfiguration) {

    }

    @Override
    public void reloadConfiguration(String worldName) {

    }

    @Override
    public void reloadConfiguration(World world) {

    }

    @Override
    public void reloadAllConfiguration() {

    }
}
