package me.mrgeneralq.sleepmost.repositories.containers;

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

    public boolean isAnimationRunningAt(World world) {
        return this.runningWorldsAnimation.contains(world.getUID());
    }


    public void setPlayerSleeping(Player player, boolean sleeping) {

        UUID playerUUID = player.getUniqueId();
        UUID worldUUID = player.getWorld().getUID();

        if(sleeping)
           this.sleepingPlayers.computeIfAbsent(worldUUID, w -> new HashSet<>()).add(playerUUID);

        else if(anyoneSleepingAt(player.getWorld()))
            this.sleepingPlayers.get(worldUUID).remove(playerUUID);
    }

    public boolean anyoneSleepingAt(World world) {
        return this.sleepingPlayers.containsKey(world.getUID());
    }

    public List<Player> getSleepingPlayers(World world) {
        return sleepingPlayers.getOrDefault(world.getUID(), new HashSet<>()).stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .collect(toList());
    }
    public void clearSleepingPlayers(World world){
        this.sleepingPlayers.remove(world.getUID());
    }
}
