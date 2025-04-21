package me.mrgeneralq.sleepmost.services;

import org.bukkit.entity.Player;

public interface ICooldownService {
   boolean isCoolingDown(Player player);
   void startCooldown(Player player);
   boolean cooldownEnabled();
   void removeCooldown(Player player);
}
