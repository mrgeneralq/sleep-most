package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IBossBarService;
import me.mrgeneralq.sleepmost.repositories.BossBarRepository;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarService implements IBossBarService {

    BossBarRepository bossBarRepository;

    public BossBarService(BossBarRepository bossBarRepository) {
        this.bossBarRepository = bossBarRepository;
    }

    @Override
    public void registerBossBar(World world) {

        BossBar bossBar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        this.bossBarRepository.set(world, bossBar);

    }

    @Override
    public void unregisterBossBar(World world) {

        this.bossBarRepository.remove(world);

    }

    @Override
    public void registerPlayer(World world, Player player) {

        BossBar bossBar = this.bossBarRepository.get(world);
        bossBar.addPlayer(player);
        this.bossBarRepository.set(world, bossBar);
    }

    @Override
    public void unregisterPlayer(World world, Player player) {

        BossBar bossBar = this.bossBarRepository.get(world);
        bossBar.removePlayer(player);

    }

    @Override
    public void unregisterBossBar(World world, Player player) {

        BossBar bossBar = bossBarRepository.get(world);
        bossBar.removePlayer(player);
        this.bossBarRepository.remove(world);
    }

    @Override
    public void setVisible(World world, boolean visible) {

        BossBar bossBar = this.bossBarRepository.get(world);
        bossBar.setVisible(visible);

    }

    @Override
    public BossBar getBossBar(World world) {
        return this.bossBarRepository.get(world);
    }
}
