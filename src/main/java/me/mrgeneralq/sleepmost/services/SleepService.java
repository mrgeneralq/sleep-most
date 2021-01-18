package me.mrgeneralq.sleepmost.services;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.flags.*;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.runnables.NightcycleAnimationTask;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static me.mrgeneralq.sleepmost.enums.SleepSkipCause.*;

import java.util.stream.Stream;

public class SleepService implements ISleepService {

    private final Sleepmost main;
    private final IConfigRepository configRepository;
    private final IConfigService configService;
    private final IMessageService messageService;
    private final IFlagsRepository flagsRepository;
    private final ICooldownService cooldownService;
    private final IFlagService flagsService;
    private final DataContainer dataContainer = DataContainer.getContainer();

    private static final int
            NIGHT_START_TIME = 12541,
            NIGHT_END_TIME = 23850;

    public SleepService(Sleepmost main, IConfigService configService, IConfigRepository configRepository, IMessageService messageService, IFlagsRepository flagsRepository, ICooldownService cooldownService, IFlagService flagsService) {
        this.main = main;
        this.configService = configService;
        this.configRepository = configRepository;
        this.messageService = messageService;
        this.flagsRepository = flagsRepository;
        this.cooldownService = cooldownService;
        this.flagsService = flagsService;
    }

    /*
    General
     */

    @Override
    public void resetDay(World world, String lastSleeperName, String lastSleeperDisplayName) {

        if (!canReset(world)){
            return;
        }
        int skipDelay = this.flagsRepository.getSkipDelayFlag().getValueAt(world);

        Bukkit.getScheduler().runTaskLater(main, () -> executeSleepReset(world, lastSleeperName, lastSleeperDisplayName), skipDelay * 20L);
    }

    @Override
    public void onSleepSkip(World world, String lastSleeperName, String lastSleeperDisplayName) {

        //do the cycle animation if the world was in night
        if (!isNight(world) || !this.flagsRepository.getNightcycleAnimationFlag().getValueAt(world)){
            resetDay(world, lastSleeperName, lastSleeperDisplayName);
            return;
        }

        dataContainer.setAnimationRunning(world, true);
        new NightcycleAnimationTask(this, world, lastSleeperName).runTaskTimer(this.main, 0, 1);
    }

    @Override
    public void reloadConfig() {
        configRepository.reload();
    }

    /*
    Worlds
     */

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
    public boolean canReset(World world)
    {
        if(!resetRequired(world)){
            return false;
        }
        return getSleepingPlayersAmount(world) == getRequiredPlayersSleepingCount(world);
    }

    @Override
    public int getSleepingPlayersAmount(World world) {
        return this.dataContainer.getSleepingPlayers(world).size();
    }

    @Override
    public int getPlayerCountInWorld(World world) {
        Stream<Player> playersStream = world.getPlayers().stream();

        if(flagsRepository.getUseExemptFlag().getValueAt(world))
            playersStream = playersStream.filter(p -> !p.hasPermission("sleepmost.exempt"));

        if (flagsService.isAfkFlagUsable() && flagsRepository.getUseAfkFlag().getValueAt(world))
            playersStream = playersStream.filter(p -> PlaceholderAPI.setPlaceholders(p, "%essentials_afk%").equalsIgnoreCase("no"));

        int players = (int) playersStream.count();

        return players == 0 ? 1 : players;
    }

    @Override
    public double getSleepingPlayerPercentage(World world) {
        return getSleepingPlayersAmount(world) / getPlayerCountInWorld(world);
    }

    @Override
    public int getRequiredPlayersSleepingCount(World world) {

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
                requiredCount = 0;
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

    /*
    Players
     */

    @Override
    public void setSleeping(Player player, boolean sleeping) {

        this.dataContainer.setPlayerSleeping(player, sleeping);

        if (!this.shouldSkip(player))
            return;

        onSleepSkip(player.getWorld(), player.getName(), player.getDisplayName());
    }

    @Override
    public boolean isPlayerAsleep(Player player) {
        World world = player.getWorld();

        return this.dataContainer.getSleepingPlayers(world).contains(player);
    }

    /*
    Skip Status
     */

    @Override
    public boolean resetRequired(World world) {
        return isNight(world) || world.isThundering();
    }

    @Override
    public boolean isNight(World world) {
        return world.getTime() > NIGHT_START_TIME && world.getTime() < NIGHT_END_TIME;
    }




    private void executeSleepReset(World world, String lastSleeperName, String lastSleeperDisplayName) {
        if(isNight(world))
            world.setTime(configService.getResetTime());

        world.setThundering(false);
        world.setStorm(false);
        Bukkit.getServer().getPluginManager().callEvent(new SleepSkipEvent(world, getCurrentSkipCause(world), lastSleeperName, lastSleeperDisplayName));
    }

    private boolean shouldSkip(Player lastSleeper) {

        World world = lastSleeper.getWorld();

        if (!isEnabledAt(world) || !resetRequired(world))
            return false;

        if (this.cooldownService.cooldownEnabled() && !cooldownService.isCoolingDown(lastSleeper)) {
            messageService.sendPlayerLeftMessage(lastSleeper, getCurrentSkipCause(world));
            cooldownService.startCooldown(lastSleeper);
        }
        return true;
        //return sleepPercentageReached(world);
    }
}