package me.mrgeneralq.sleepmost.repositories.concretes;

import me.mrgeneralq.sleepmost.interfaces.IRepository;
import org.bukkit.World;
import org.bukkit.boss.BossBar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossBarRepository implements IRepository<World, BossBar> {

    private final Map<UUID, BossBar> bossBarList = new HashMap<>();

    @Override
    public BossBar get(World world) {
        return this.bossBarList.get(world.getUID());
    }

    @Override
    public void set(World world, BossBar bossBar) {
        this.bossBarList.put(world.getUID(), bossBar);
    }

    @Override
    public boolean exists(World world) {
        return this.bossBarList.containsKey(world.getUID());
    }

    @Override
    public void remove(World world) {
        this.bossBarList.remove(world);
    }
}
