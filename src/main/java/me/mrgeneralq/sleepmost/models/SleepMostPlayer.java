package me.mrgeneralq.sleepmost.models;

import org.bukkit.OfflinePlayer;

public class SleepMostPlayer {

    private final OfflinePlayer player;
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
}
