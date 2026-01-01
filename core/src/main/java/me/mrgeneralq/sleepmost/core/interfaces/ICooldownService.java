package me.mrgeneralq.sleepmost.core.interfaces;

import org.bukkit.entity.Player;

public interface ICooldownService {
   boolean isCoolingDown(Player player);
   void startCooldown(Player player);
   boolean cooldownEnabled();
   void removeCooldown(Player player);
}
