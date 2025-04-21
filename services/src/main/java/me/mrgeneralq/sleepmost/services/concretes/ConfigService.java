package me.mrgeneralq.sleepmost.services.concretes;

import com.google.inject.Inject;
import me.mrgeneralq.sleepmost.services.IConfigService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ConfigService implements IConfigService {

    private final Plugin plugin;

    @Inject
    public ConfigService(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int getResetTime() {
        return plugin.getConfig().getInt("time-after-reset");
    }

    @Override
    public boolean updateCheckerEnabled() {
        return plugin.getConfig().getBoolean("update-checker-enabled");
    }


    @Override
    public Set<World> getEnabledWorlds()
    {
        return plugin.getConfig().getConfigurationSection("sleep").getKeys(false).stream()
                .map(Bukkit::getWorld)
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    @Override
    public boolean debugModeEnabled() {
        return plugin.getConfig().getBoolean("debug-mode");
    }

    @Override
    public int getNightcycleAnimationSpeed() {
        return plugin.getConfig().getInt("nightcycle-animation-speed");
    }

    @Override
    public int getNightcycleAnimationSpeedMax() {
        return plugin.getConfig().getInt("nightcycle-animation-speed-max");
    }

    @Override
    public int getNightStartTime() {
        return plugin.getConfig().getInt("time.start-time");
    }

    @Override
    public int getNightStopTime() {
        return plugin.getConfig().getInt("time.end-time");
    }

    @Override
    public String getAFKPlaceholder() {
        return plugin.getConfig().getString("afk-handling.placeholder");
    }

    @Override
    public String getAFKPositiveResult() {
        return plugin.getConfig().getString("afk-handling.positive-result");
    }

}
