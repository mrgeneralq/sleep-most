package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

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
    public Set<World> getEnabledWorlds()
    {
        return main.getConfig().getConfigurationSection("sleep").getKeys(false).stream()
                .map(Bukkit::getWorld)
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    @Override
    public boolean debugModeEnabled() {
        return main.getConfig().getBoolean("debug-mode");
    }

    @Override
    public int getNightcycleAnimationSpeed() {
        return main.getConfig().getInt("nightcycle-animation-speed");
    }

    @Override
    public int getNightcycleAnimationSpeedMax() {
        return main.getConfig().getInt("nightcycle-animation-speed-max");
    }

    @Override
    public int getNightStartTime() {
        return main.getConfig().getInt("time.start-time");
    }

    @Override
    public int getNightStopTime() {
        return main.getConfig().getInt("time.end-time");
    }

    @Override
    public String getAFKPlaceholder() {
        return main.getConfig().getString("afk-handling.placeholder");
    }

    @Override
    public String getAFKPositiveResult() {
        return main.getConfig().getString("afk-handling.positive-result");
    }

}
