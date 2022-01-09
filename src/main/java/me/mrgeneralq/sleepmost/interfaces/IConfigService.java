package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.World;

import java.util.Set;

public interface IConfigService {

    int getResetTime();
    boolean updateCheckerEnabled();
    boolean getTitleNightSkippedEnabled();
    String getTitleNightSkippedTitle();
    String getTitleNightSkippedSubTitle();
    boolean getTitleStormSkippedEnabled();
    String getTitleStormSkippedTitle();
    String getTitleStormSkippedSubTitle();
    String getNightSkippedSound();
    String getStormSkippedSound();
    Set<World> getEnabledWorlds();
}
