package me.mrgeneralq.sleepmost.services.concretes;
import me.mrgeneralq.sleepmost.interfaces.ISleepMostWorldService;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.repositories.SleepMostWorldRepository;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.GameRule;
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
    public void freezeTime(World world, Calendar calendar) {

        if(ServerVersion.CURRENT_VERSION.supportsGameRules())
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

        SleepMostWorld sleepMostWorld = this.repository.get(world.getUID());
        sleepMostWorld.setFrozen(true, calendar);
        this.repository.set(world.getUID(), sleepMostWorld);
    }

    @Override
    public void unfreezeTime(World world) {

        if(ServerVersion.CURRENT_VERSION.supportsGameRules())
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);

        SleepMostWorld sleepMostWorld = this.repository.get(world.getUID());
        sleepMostWorld.setFrozen(false, null);
        sleepMostWorld.setPlannedFrozen(false);
        this.repository.set(world.getUID(), sleepMostWorld);
    }

    @Override
    public SleepMostWorld getWorld(World world) {
        return this.repository.get(world.getUID());
    }

    @Override
    public void updateWorld(SleepMostWorld sleepMostWorld) {
        this.repository.set(sleepMostWorld.getWorld().getUID(), sleepMostWorld);
    }

    @Override
    public boolean worldExists(World world) {
        return this.repository.exists(world.getUID());
    }

}
