package me.qintinator.sleepmost.interfaces;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface ICooldownService {
   boolean isCoolingDown(Player player);
   void startCooldown(Player player);
   boolean cooldownEnabled();
   void removeCooldown(Player player);
}
