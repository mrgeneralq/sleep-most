package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.models.WorldConfiguration;
import org.bukkit.World;

public interface IWorldConfigurationService {

    WorldConfiguration createConfiguration(String worldName);
    WorldConfiguration getConfiguration(String worldName);
    void updateConfiguration(WorldConfiguration worldConfiguration);
    void reloadConfiguration(String worldName);
    void reloadConfiguration(World world);
    void reloadAllConfiguration();

}
