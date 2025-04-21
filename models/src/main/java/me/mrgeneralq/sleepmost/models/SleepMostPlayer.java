package me.mrgeneralq.sleepmost.models;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SleepMostPlayer {

    private final UUID playerUUID;

    private final Map<UUID, Boolean> worldInsomniaStatus = new HashMap<>();

    private boolean hasInsomnia = false;
    private boolean debugMode = false;


    public SleepMostPlayer(OfflinePlayer player) {
        this.playerUUID = player.getUniqueId();
    }

    public OfflinePlayer getPlayerUUID() {
        return Bukkit.getOfflinePlayer(this.playerUUID);
    }

    public boolean isHasInsomnia() {
        return hasInsomnia;
    }

    public void setHasInsomnia(boolean hasInsomnia) {
        this.hasInsomnia = hasInsomnia;
    }

    public void setInsomniaStatus(World world, boolean hasInsomnia){
        this.worldInsomniaStatus.put(world.getUID(), hasInsomnia);
    }

    public boolean getInsomniaStatus(World world){
        return this.worldInsomniaStatus.getOrDefault(world.getUID(), false);
    }

    public boolean hasDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
