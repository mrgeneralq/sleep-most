package me.mrgeneralq.sleepmost.core.interfaces;

import org.bukkit.World;

import java.util.Set;

public interface IConfigService {

    int getResetTime();
    boolean updateCheckerEnabled();
    Set<World> getEnabledWorlds();
    boolean debugModeEnabled();

    int getNightcycleAnimationSpeed();
    int getNightcycleAnimationSpeedMax();
    int getNightStartTime();
    int getNightStopTime();

    String getAFKPlaceholder();

    String getAFKPositiveResult();
}
