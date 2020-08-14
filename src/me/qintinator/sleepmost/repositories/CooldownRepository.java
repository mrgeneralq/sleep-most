package me.qintinator.sleepmost.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.qintinator.sleepmost.interfaces.ICooldownRepository;

public class CooldownRepository implements ICooldownRepository {

    private final Map<UUID, Long> cooldownMap = new HashMap<>();

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
