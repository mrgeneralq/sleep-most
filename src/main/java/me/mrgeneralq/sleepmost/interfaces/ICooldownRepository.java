package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.entity.Player;

public interface ICooldownRepository {

    long getPlayerCooldown(Player player);
    void setCooldown(Player player);
    boolean contains(Player player);
    void removeCooldown(Player player);

}
