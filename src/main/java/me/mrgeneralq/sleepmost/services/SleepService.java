package me.mrgeneralq.sleepmost.services;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.runnables.NightcycleAnimationTask;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static me.mrgeneralq.sleepmost.enums.SleepSkipCause.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepService implements ISleepService {

    private final Sleepmost main;
    private final IConfigRepository configRepository;
    private final IConfigService configService;
    private final IFlagsRepository flagsRepository;
    private final IFlagService flagService;
    private final DataContainer dataContainer = DataContainer.getContainer();

    private static final int
            NIGHT_START_TIME = 12541,
            NIGHT_END_TIME = 23850;

    public SleepService(Sleepmost main, IConfigService configService, IConfigRepository configRepository, IFlagsRepository flagsRepository, IFlagService flagService) {
        this.main = main;
        this.configService = configService;
        this.configRepository = configRepository;
        this.flagsRepository = flagsRepository;
        this.flagService = flagService;
    }

    @Override
    public void reloadConfig() {
        configRepository.reload();
    }


    @Override
    public boolean isEnabledAt(World world) {
        return configRepository.containsWorld(world);
    }

    @Override
    public void enableAt(World world) {
        configRepository.enableForWorld(world);
    }

    @Override
    public void disableAt(World world) {
        configRepository.disableForWorld(world);
    }

    @Override
    public int getSleepersAmount(World world) {
        return this.dataContainer.getSleepingPlayers(world).size();
    }

    @Override
    public List<Player> getSleepers(World world){
        return new ArrayList<>(this.dataContainer.getSleepingPlayers(world));
    }

    @Override
    public int getPlayerCountInWorld(World world) {

        Stream<Player> playersStream = world.getPlayers().stream();

        if(flagsRepository.getUseNonSurvivalModeFlag().getValueAt(world))
            playersStream = playersStream.filter(p -> p.getGameMode() == GameMode.SURVIVAL);

        if(flagsRepository.getUseExemptFlag().getValueAt(world))
            playersStream = playersStream.filter(p -> !p.hasPermission("sleepmost.exempt"));

         if (flagService.isAfkFlagUsable() && flagsRepository.getUseAfkFlag().getValueAt(world))
            playersStream = playersStream.filter(p -> PlaceholderAPI.setPlaceholders(p, "%essentials_afk%").equalsIgnoreCase("no"));

         int belowYFlagValue = flagsRepository.getExemptBelowYFlag().getValueAt(world);
         if(belowYFlagValue > -1)
             playersStream = playersStream.filter(p -> p.getLocation().getY() > belowYFlagValue);

         int playerCount = (int) playersStream.count();

         return playerCount == 0? 1: playerCount;
    }

    @Override
    public double getSleepersPercentage(World world) {
        return getSleepersAmount(world) / getPlayerCountInWorld(world);
    }

    @Override
    public int getRequiredSleepersCount(World world) {

        int requiredCount;

        switch (this.flagsRepository.getCalculationMethodFlag().getValueAt(world)) {

            case PERCENTAGE_REQUIRED:
                double percentageRequired = this.flagsRepository.getPercentageRequiredFlag().getValueAt(world);
                requiredCount = (int) Math.ceil(getPlayerCountInWorld(world) * percentageRequired);
                break;

            case PLAYERS_REQUIRED:
                int requiredPlayers = this.flagsRepository.getPlayersRequiredFlag().getValueAt(world);
                requiredCount = (requiredPlayers <= getPlayerCountInWorld(world)) ? requiredPlayers : getPlayerCountInWorld(world);
                break;

            default:
                requiredCount = 1;
        }
        return requiredCount;
    }

    @Override
    public SleepSkipCause getCurrentSkipCause(World world) {

        if(isNight(world))
            return NIGHT_TIME;

        if(world.isThundering())
            return STORM;

        return UNKNOWN;
    }

    @Override
    public boolean isRequiredCountReached(World world) {
        return this.getSleepersAmount(world) >= this.getRequiredSleepersCount(world);
    }

    @Override
    public int getRemainingSleepers(World world){
        return this.getRequiredSleepersCount(world) - this.getSleepersAmount(world);
    }

    @Override
    public void clearSleepersAt(World world)
    {
        this.dataContainer.clearSleepingPlayers(world);
    }

    @Override
    public void setSleeping(Player player, boolean sleeping) {

        World world = player.getWorld();

        this.dataContainer.setPlayerSleeping(player, sleeping);

        if (!this.shouldSkip(world))
            return;

        SleepSkipCause skipCause = this.getCurrentSkipCause(world);

        int skipDelay = this.flagsRepository.getSkipDelayFlag().getValueAt(world);

        Bukkit.getScheduler().runTaskLater(main, () ->
        {
            if(!shouldSkip(world)){
                return;
            }
            if(this.flagsRepository.getNightcycleAnimationFlag().getValueAt(world)){
                runSkipAnimation(player, skipCause);
                return;
            }

            List<OfflinePlayer> peopleWhoSlept = this.getSleepers(world).stream().map(p -> Bukkit.getOfflinePlayer(p.getUniqueId())).collect(Collectors.toList());

            this.executeSleepReset(world, player.getName(), player.getDisplayName(), peopleWhoSlept, skipCause);
        }, skipDelay * 20L);
    }

    @Override
    public boolean isPlayerAsleep(Player player) {
        World world = player.getWorld();

        return this.dataContainer.getSleepingPlayers(world).contains(player);
    }


    @Override
    public boolean resetRequired(World world) {
        return isNight(world) || world.isThundering();
    }

    @Override
    public boolean isNight(World world) {
        return world.getTime() > NIGHT_START_TIME && world.getTime() < NIGHT_END_TIME;
    }

    @Override
    public void executeSleepReset(World world, String lastSleeperName, String lastSleeperDisplayName, List<OfflinePlayer> peopleWhoSlept, SleepSkipCause skipCause) {
        if(isNight(world))
            world.setTime(configService.getResetTime());

        if(this.flagsRepository.getSkipStormFlag().getValueAt(world) || skipCause == SleepSkipCause.STORM){
        world.setThundering(false);
        world.setStorm(false);
        }

        Bukkit.getServer().getPluginManager().callEvent(new SleepSkipEvent(world,peopleWhoSlept ,skipCause, lastSleeperName, lastSleeperDisplayName));
    }

    private boolean shouldSkip(World world) {
        return isEnabledAt(world) &&
                resetRequired(world) &&
                isRequiredCountReached(world);
    }

    private void runSkipAnimation(Player player, SleepSkipCause sleepSkipCause) {
        World world = player.getWorld();

        if(this.dataContainer.isAnimationRunningAt(world))
            return;

        List<OfflinePlayer> sleepingPlayers = this.getSleepers(world).stream().map(p -> Bukkit.getOfflinePlayer(p.getUniqueId())).collect(Collectors.toList());

        dataContainer.setAnimationRunning(world, true);
        new NightcycleAnimationTask(this, this.flagsRepository , world, player, sleepingPlayers , sleepSkipCause).runTaskTimer(this.main, 0, 1);
    }
}