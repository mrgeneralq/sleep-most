package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public interface IBossBarService {

    void registerBossBar(World world);
    void unregisterBossBar(World world);
    void registerPlayer(World world, Player player);
    void unregisterPlayer(World world, Player player);
    void unregisterBossBar(World world, Player player);
    void setVisible(World world, boolean visible);
    BossBar getBossBar(World world);

}
