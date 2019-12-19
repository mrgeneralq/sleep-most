package me.qintinator.sleepmost.services;
import me.qintinator.sleepmost.enums.SleepSkipCause;
import me.qintinator.sleepmost.events.SleepSkipEvent;
import me.qintinator.sleepmost.interfaces.IConfigRepository;
import me.qintinator.sleepmost.interfaces.ISleepService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class SleepService implements ISleepService {

    private final IConfigRepository configRepository;

    public SleepService(IConfigRepository configRepository){
        this.configRepository = configRepository;
    }

    @Override
    public boolean enabledForWorld(World world) {
       return configRepository.containsWorld(world);
    }

    @Override
    public boolean sleepPercentageReached(World world) {

        return this.getPlayersSleepingCount(world) >= this.getRequiredPlayersSleepingCount(world);
    }

    @Override
    public double getPercentageRequired(World world) {
        return configRepository.getPercentageRequired(world);
    }

    @Override
    public boolean getMobNoTarget(World world) {
        return configRepository.getMobNoTarget(world);
    }

    @Override
    public double getSleepingPlayerPercentage(World world) {
        return getPlayersSleepingCount(world) / getPlayerCountInWorld(world);
    }

    @Override
    public int getPlayersSleepingCount(World world) {
        return Bukkit.getWorld(world.getName()).getPlayers().stream().filter(p -> p.isSleeping()).collect(Collectors.toList()).size() + 1;
    }

    @Override
    public int getRequiredPlayersSleepingCount(World world) {
        return (int) Math.ceil(getPlayerCountInWorld(world) * getPercentageRequired(world));
    }

    @Override
    public int getPlayerCountInWorld(World world) {
       return Bukkit.getWorld(world.getName()).getPlayers().size();
    }

    @Override
    public void resetDay(World world) {
        SleepSkipCause cause;

        if (this.isNight(world)) {
            cause = SleepSkipCause.NightTime;
            world.setTime(0);
        } else {
            cause = SleepSkipCause.Storm;
        }

        world.setThundering(false);
        world.setStorm(false);
        Bukkit.getServer().getPluginManager().callEvent(new SleepSkipEvent(world, cause));
    }

    @Override
    public boolean resetRequired(World world) {

        //check if the world is night or thundering
        if(this.isNight(world) || world.isThundering())
            return true;
        return false;
    }

    @Override
    public boolean isNight(World world) {
            return (world.getTime() > 12541 && world.getTime() < 23850);
    }

    @Override
    public SleepSkipCause getSleepSkipCause(World world) {
        if (this.isNight(world))
            return SleepSkipCause.NightTime;
        return SleepSkipCause.Storm;
    }

}
