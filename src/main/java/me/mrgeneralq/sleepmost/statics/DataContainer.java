package me.mrgeneralq.sleepmost.statics;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

public class DataContainer {

    private static DataContainer instance;

    private final Set<UUID> runningWorldsAnimation;
    private final Map<UUID, Set<UUID>> sleepingPlayers;

    private DataContainer() {

        runningWorldsAnimation = new HashSet<>();
        sleepingPlayers = new HashMap<>();
    }

    public static DataContainer getContainer() {
        if (instance == null)
            instance = new DataContainer();
        return instance;
    }

    public boolean animationRunning(World world) {
        return this.runningWorldsAnimation.contains(world.getUID());
    }

    public void setAnimationRunning(World world, boolean running) {

        if (running)
            this.runningWorldsAnimation.add(world.getUID());
        else
            this.runningWorldsAnimation.remove(world.getUID());
    }


    public void setPlayerSleeping(Player player, boolean sleeping) {

        UUID worldID = player.getWorld().getUID();
        UUID playerID = player.getUniqueId();

        Set<UUID> sleepingPlayers = this.sleepingPlayers.get(worldID);

        if (sleeping)
            sleepingPlayers.add(playerID);
        else
            sleepingPlayers.remove(playerID);

        this.sleepingPlayers.put(worldID, sleepingPlayers);
    }

    public List<Player> getSleepingPlayers(World world) {
        return sleepingPlayers.get(world.getUID()).stream().map(Bukkit::getPlayer).collect(Collectors.toList());
    }
}
