package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.models.WorldProperty;
import org.bukkit.World;

public interface IWorldPropertyService {

    WorldProperty getWorldProperties(World world);
    void createNewWorldProperty(World world);
    void setWorldProperty(World world, WorldProperty property);
    boolean propertyExists(World world);
}
