package me.mrgeneralq.sleepmost.core.statics;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public interface MaxHPHealer {
    void heal(Player player);

    MaxHPHealer
            LEGACY_HEALER = player -> player.setHealth(20),
            UPDATED_HEALER = player ->  player.setHealth(player.getAttribute(Attribute.MAX_HEALTH).getBaseValue());
}
