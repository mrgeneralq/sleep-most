package me.mrgeneralq.sleepmost.services;
import me.mrgeneralq.sleepmost.interfaces.ISleepMostWorldService;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.repositories.SleepMostWorldRepository;
import org.bukkit.World;

import java.util.Calendar;

public class SleepMostWorldService implements ISleepMostWorldService {


    private final SleepMostWorldRepository repository;

    public SleepMostWorldService(SleepMostWorldRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerWorld(World world) {

        SleepMostWorld sleepMostWorld = new SleepMostWorld(world);
        this.repository.set(sleepMostWorld.getWorld().getUID(), sleepMostWorld);

    }

    @Override
    public void unregisterWorld(World world) {
        this.repository.remove(world.getUID());
    }

    @Override
    public void freezeTime(World world) {
        SleepMostWorld sleepMostWorld = this.repository.get(world.getUID());
        sleepMostWorld.setFrozen(true);
    }

    @Override
    public boolean isFrozen(World world) {
        SleepMostWorld sleepMostWorld = this.repository.get(world.getUID());
        return sleepMostWorld.isFrozen();
    }

    @Override
    public Calendar getFrozenSince(World world) {
        SleepMostWorld sleepMostWorld = this.repository.get(world.getUID());
        return sleepMostWorld.getFrozenSince();
    }
}
