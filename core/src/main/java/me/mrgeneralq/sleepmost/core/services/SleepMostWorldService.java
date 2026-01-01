package me.mrgeneralq.sleepmost.core.services;
import me.mrgeneralq.sleepmost.core.interfaces.IGameRuleService;
import me.mrgeneralq.sleepmost.core.interfaces.ISleepMostWorldService;
import me.mrgeneralq.sleepmost.core.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.core.repositories.SleepMostWorldRepository;
import me.mrgeneralq.sleepmost.core.statics.ServerVersion;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.util.Calendar;

public class SleepMostWorldService implements ISleepMostWorldService {

    private final IGameRuleService gameRuleService;
    private final SleepMostWorldRepository repository;

    public SleepMostWorldService(IGameRuleService gameRuleService, SleepMostWorldRepository repository) {
        this.gameRuleService = gameRuleService;
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
            this.gameRuleService.setAdvanceTime(world, false);

        SleepMostWorld sleepMostWorld = this.repository.get(world.getUID());
        sleepMostWorld.setFrozen(true, calendar);
        this.repository.set(world.getUID(), sleepMostWorld);
    }

    @Override
    public void unfreezeTime(World world) {

        if(ServerVersion.CURRENT_VERSION.supportsGameRules())
        this.gameRuleService.setAdvanceTime(world, true);

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
