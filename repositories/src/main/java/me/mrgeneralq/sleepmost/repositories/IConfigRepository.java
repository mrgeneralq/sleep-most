package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import org.bukkit.World;

public interface IConfigRepository {

    //General
    String getPrefix();
    int getCooldown();
    String getString(String path);
    Object get(String path);
    void reload();

    //Worlds
    void addWorld(World world);
    @Deprecated void removeWorld(World world);
    boolean containsWorld(World world);
    void disableForWorld(World world);
    void enableForWorld(World world);

    //Flags
    void setFlagValue(ISleepFlag<?> flag, World world, Object value);
    Object getFlagValue(ISleepFlag<?> flag, World world);
}
