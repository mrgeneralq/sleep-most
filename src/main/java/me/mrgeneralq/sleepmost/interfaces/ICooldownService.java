package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.entity.Player;

public interface ICooldownService {
   boolean isCoolingDown(Player player);
   void startCooldown(Player player);
   boolean cooldownEnabled();
   void removeCooldown(Player player);
}
