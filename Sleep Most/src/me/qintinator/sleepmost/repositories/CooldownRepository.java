package me.qintinator.sleepmost.repositories;

import me.qintinator.sleepmost.interfaces.ICooldownRepository;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class CooldownRepository implements ICooldownRepository {

    private final HashMap<UUID, Long> cooldownMap = new HashMap<>();

    @Override
    public long getPlayerCooldown(Player player) {
        return cooldownMap.get(player.getUniqueId());
    }

    @Override
    public void setCooldown(Player player) {
        cooldownMap.put(player.getUniqueId(), new Date().getTime());
    }

    @Override
    public boolean contains(Player player) {
        return this.cooldownMap.containsKey(player.getUniqueId());
    }

    @Override
    public void removeCooldown(Player player) {
        cooldownMap.remove(player.getUniqueId());
    }


}
