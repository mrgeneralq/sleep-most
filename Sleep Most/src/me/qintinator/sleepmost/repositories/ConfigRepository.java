package me.qintinator.sleepmost.repositories;

import me.qintinator.sleepmost.Main;
import me.qintinator.sleepmost.interfaces.IConfigRepository;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class ConfigRepository implements IConfigRepository {
    private final Main main;

    public ConfigRepository(Main main) {
        this.main = main;
    }

    @Override
    public double getPercentageRequired(World world) {
        return main.getConfig().getDouble(String.format("sleep.%s.percentage-required", world.getName()));
    }

    @Override
    public boolean containsWorld(World world) {
        return main.getConfig().isConfigurationSection(String.format("sleep.%s", world.getName()));
    }

    @Override
    public String getString(String string) {
        return main.getConfig().getString(string);
    }

    @Override
    public int getCooldown() {
        return main.getConfig().getInt("messages.cooldown");
    }

    @Override
    public boolean getMobNoTarget(World world) {
        return main.getConfig().getBoolean(String.format("sleep.%s.mob-no-target", world.getName()));
    }

    @Override
    public boolean getUseExempt(World world) {
        return main.getConfig().getBoolean(String.format("sleep.%s.use-exempt", world.getName()));
    }

    @Override
    public String getPrefix() {
        return main.getConfig().getString("messages.prefix");
    }

    @Override
    public void reloadConfig() {
        main.reloadConfig();
    }

    @Override
    public void addWorld(World world) {

        String worldName = world.getName();

        main.getConfig().createSection(String.format("sleep.%s", world.getName()));
        main.getConfig().set(String.format("sleep.%s.percentage-required", world.getName()), 0.5);
        main.getConfig().set(String.format("sleep.%s.mob-no-target", world.getName()), true);
        main.getConfig().set(String.format("sleep.%s.use-exempt", world.getName()),true);
        main.getConfig().set(String.format("sleep.%s.prevent-sleep",world.getName()),false);
        main.getConfig().set(String.format("sleep.%s.prevent-phantom", world.getName()), false);
        main.getConfig().set(String.format("sleep.%s.nightcycle-animation", world.getName()), false);
        main.getConfig().set(String.format("sleep.%s.storm-sleep", world.getName()), false);
        main.saveConfig();
    }

    @Override
    public void removeWorld(World world) {
        main.getConfig().set(String.format("sleep.%s", world.getName()),null);
        main.saveConfig();
    }

    @Override
    public void setFlag(World world, String flag, Object value) {
        main.getConfig().set(String.format("sleep.%s.%s",world.getName(),flag),value);
        main.saveConfig();
    }

    @Override
    public Object getFlag(World world, String flag) {

       return main.getConfig().get(String.format("sleep.%s.%s",world.getName(),flag));
    }
}
