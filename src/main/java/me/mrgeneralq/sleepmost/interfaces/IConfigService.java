package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.Sound;

public interface IConfigService {

    int getResetTime();

    boolean updateCheckerEnabled();

    boolean getTitleNightSkippedEnabled();

    String getTitleNightSkippedTitle();

    String getTitleNightSkippedSubTitle();

    boolean getTitleStormSkippedEnabled();

    String getTitleStormSkippedTitle();

    String getTitleStormSkippedSubTitle();

    boolean getSoundNightSkippedEnabled();

    Sound getSoundNightSkippedSound();

    boolean getSoundStormSkippedEnabled();

    Sound getSoundStormSkippedSound();
}
