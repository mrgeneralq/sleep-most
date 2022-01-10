package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;

import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static me.mrgeneralq.sleepmost.statics.ChatColorUtils.colorize;

public class ConfigService implements IConfigService {

    private final Sleepmost main;

    public ConfigService(Sleepmost main) {
        this.main = main;
    }

    @Override
    public int getResetTime() {
        return main.getConfig().getInt("time-after-reset");
    }

    @Override
    public boolean updateCheckerEnabled() {
        return main.getConfig().getBoolean("update-checker-enabled");
    }


    @Override
    public boolean getTitleNightSkippedEnabled() {
        return main.getConfig().getBoolean("titles.night-skipped.enabled");
    }

    @Override
    public String getTitleNightSkippedTitle() {
        return colorize(main.getConfig().getString("titles.night-skipped.title"));
    }

    @Override
    public String getTitleNightSkippedSubTitle() {
        return colorize(main.getConfig().getString("titles.night-skipped.subtitle"));
    }

    @Override
    public boolean getTitleStormSkippedEnabled() {
        return main.getConfig().getBoolean("titles.storm-skipped.enabled");
    }

    @Override
    public String getTitleStormSkippedTitle() {
        return colorize(main.getConfig().getString("titles.storm-skipped.title"));
    }

    @Override
    public String getTitleStormSkippedSubTitle() {
        return colorize(main.getConfig().getString("titles.storm-skipped.subtitle"));
    }

    @Override
    public Set<World> getEnabledWorlds()
    {
        return main.getConfig().getConfigurationSection("sleep").getKeys(false).stream()
                .map(Bukkit::getWorld)
                .filter(Objects::nonNull)
                .collect(toSet());
    }
}
