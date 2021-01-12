package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import org.bukkit.World;

public class ConfigRepository implements IConfigRepository {
    private final Sleepmost main;

    public ConfigRepository(Sleepmost main) {
        this.main = main;
    }

    @Override
    public double getPercentageRequired(World world) {
        return main.getConfig().getDouble(String.format("sleep.%s.percentage-required", world.getName()));
    }


    @Deprecated
    @Override
    public boolean containsWorld(World world) {
        return main.getConfig().getBoolean(String.format("sleep.%s.enabled", world.getName()));
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

        main.getConfig().createSection(String.format("sleep.%s", world.getName()));
        main.getConfig().set(String.format("sleep.%s.enabled", world.getName()), true);
        main.getConfig().set(String.format("sleep.%s.calculation-method", world.getName()), "percentage");
        main.getConfig().set(String.format("sleep.%s.percentage-required", world.getName()), 0.5);
        main.getConfig().set(String.format("sleep.%s.players-required", world.getName()), 5);
        main.getConfig().set(String.format("sleep.%s.mob-no-target", world.getName()), true);
        main.getConfig().set(String.format("sleep.%s.use-exempt", world.getName()),true);
        main.getConfig().set(String.format("sleep.%s.use-afk", world.getName()), false);
        main.getConfig().set(String.format("sleep.%s.prevent-sleep",world.getName()),false);
        main.getConfig().set(String.format("sleep.%s.prevent-phantom", world.getName()), false);
        main.getConfig().set(String.format("sleep.%s.nightcycle-animation", world.getName()), false);
        main.getConfig().set(String.format("sleep.%s.storm-sleep", world.getName()), false);
        main.saveConfig();
    }

    @Deprecated
    @Override
    public void removeWorld(World world) {
        main.getConfig().set(String.format("sleep.%s", world.getName()),null);
        main.saveConfig();
    }

    @Override
    public void disableForWorld(World world) {
        main.getConfig().set(String.format("sleep.%s.enabled", world.getName()), false);
        main.saveConfig();
    }

    @Override
    public void setFlagValue(ISleepFlag<?> flag, World world, Object value)
    {
        main.getConfig().set(getValuePath(flag, world), value);
        main.saveConfig();
    }

    @Override
    public Object getFlagValue(ISleepFlag<?> flag, World world) {
        return main.getConfig().get(getValuePath(flag, world));
    }

    private static String getValuePath(ISleepFlag<?> flag, World world) {
        return String.format("sleep.%s.%s", world.getName(), flag.getName());
    }
}
