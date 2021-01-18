package me.mrgeneralq.sleepmost.interfaces;

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
    <V> void setFlagValue(ISleepFlag<V> flag, World world, V value);
    Object getFlagValue(ISleepFlag<?> flag, World world);
}
