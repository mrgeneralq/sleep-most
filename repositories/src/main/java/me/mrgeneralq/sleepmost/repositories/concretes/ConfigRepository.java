package me.mrgeneralq.sleepmost.repositories.concretes;

import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import org.bukkit.World;

public class ConfigRepository implements IConfigRepository
{
    private final Sleepmost main;

    public ConfigRepository(Sleepmost main) {
        this.main = main;
    }

    /*
    General
     */

    @Override
    public String getPrefix() {
        return main.getConfig().getString("messages.prefix");
    }

    @Override
    public int getCooldown() {
        return main.getConfig().getInt("messages.cooldown");
    }

    @Override
    public String getString(String path) {
        return main.getConfig().getString(path);
    }

    @Override
    public Object get(String path)
    {
        return this.main.getConfig().get(path);
    }

    @Override
    public void reload() {
        main.reloadConfig();
    }

    /*
    Worlds
     */

    @Override
    public void addWorld(World world) {

        String worldName = world.getName();

        //use a loop for this
        main.getConfig().createSection(String.format("sleep.%s", worldName));
        main.getConfig().set(String.format("sleep.%s.enabled", worldName), true);
        main.getConfig().set(getFlagValuePath("calculation-method", worldName), "percentage");
        main.getConfig().set(getFlagValuePath("percentage-required", worldName), 0.5);
        main.getConfig().set(getFlagValuePath("players-required", worldName), 5);
        main.getConfig().set(getFlagValuePath("mob-no-target", worldName), true);
        main.getConfig().set(getFlagValuePath("use-exempt", worldName), true);
        main.getConfig().set(getFlagValuePath("use-afk", worldName), false);
        main.getConfig().set(getFlagValuePath("prevent-sleep", worldName), false);
        main.getConfig().set(getFlagValuePath("prevent-phantom", worldName), false);
        main.getConfig().set(getFlagValuePath("nightcycle-animation", worldName), false);
        main.getConfig().set(getFlagValuePath("storm-sleep", worldName), false);
        main.getConfig().set(getFlagValuePath("skip-delay", worldName), 0);
        main.getConfig().set(getFlagValuePath("heal", worldName), false);
        main.getConfig().set(getFlagValuePath("feed", worldName), false);
        main.saveConfig();
    }

    @Deprecated
    @Override
    public void removeWorld(World world) {
        main.getConfig().set(String.format("sleep.%s", world.getName()),null);
        main.saveConfig();
    }

    @Deprecated
    @Override
    public boolean containsWorld(World world) {
        return worldExists(world.getName()) &&
                main.getConfig().getBoolean(String.format("sleep.%s.enabled", world.getName()));
    }

    @Override
    public void disableForWorld(World world) {
        setWorldStatus(world, false);
    }

    @Override
    public void enableForWorld(World world) {
        setWorldStatus(world, true);
    }

    /*
    Flags
     */

    @Override
    public void setFlagValue(ISleepFlag<?> flag, World world, Object value) {
        String valuePath = getFlagValuePath(flag.getName(), world.getName());

        main.getConfig().set(valuePath, value);
        main.saveConfig();
    }

    @Override
    public Object getFlagValue(ISleepFlag<?> flag, World world) {
        String valuePath = getFlagValuePath(flag.getName(), world.getName());

        return main.getConfig().get(valuePath);
    }


    private boolean worldExists(String worldName){
        return main.getConfig().isConfigurationSection(String.format("sleep.%s", worldName));
    }
    private void setWorldStatus(World world, boolean status){
        String worldName = world.getName();

        if(!worldExists(worldName)){
            addWorld(world);
        }
        main.getConfig().set(String.format("sleep.%s.enabled", worldName), status);
        main.saveConfig();
    }
    private String getFlagValuePath(String flagName, String worldName) {
        return String.format("sleep.%s.%s", worldName, flagName);
    }
}
