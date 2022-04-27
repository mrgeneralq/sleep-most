package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.World;

import java.util.Set;

public interface IConfigService {

    int getResetTime();
    boolean updateCheckerEnabled();
    Set<World> getEnabledWorlds();

}
