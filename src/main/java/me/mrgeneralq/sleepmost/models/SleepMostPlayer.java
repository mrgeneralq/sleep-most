package me.mrgeneralq.sleepmost.models;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SleepMostPlayer {

    private final OfflinePlayer player;

    private final Map<UUID, Boolean> worldInsomniaStatus = new HashMap<>();

    private boolean hasInsomnia = false;

    public SleepMostPlayer(OfflinePlayer player) {
        this.player = player;
    }

    public OfflinePlayer getPlayer() {
        return player;
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
}
