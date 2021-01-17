package me.mrgeneralq.sleepmost.statics;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class DataContainer {

    private static DataContainer instance;

    private final Set<UUID> runningWorldsAnimation = new HashSet<>();
    private final Map<UUID, Set<UUID>> sleepingPlayers = new HashMap<>();

    private DataContainer(){}

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

        Set<UUID> sleepingPlayers = this.sleepingPlayers.computeIfAbsent(worldID, w -> new HashSet<>());

        if (sleeping)
            sleepingPlayers.add(playerID);
        else
            sleepingPlayers.remove(playerID);

        this.sleepingPlayers.put(worldID, sleepingPlayers);
    }

    public List<Player> getSleepingPlayers(World world) {
        return sleepingPlayers.getOrDefault(world.getUID(), new HashSet<>()).stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .collect(toList());
    }
    public void clearSleepingPlayers(World world){
        this.sleepingPlayers.getOrDefault(world.getUID(), new HashSet<>()).clear();
    }
}
