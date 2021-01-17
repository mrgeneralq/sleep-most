package me.mrgeneralq.sleepmost.services;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.runnables.NightcycleAnimationTask;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static java.util.stream.Collectors.toList;

import java.util.List;

public class SleepService implements ISleepService {

    private final Sleepmost plugin;
    private final IConfigRepository configRepository;
    private final IConfigService configService;
    private final IMessageService messageService;
    private final IFlagsRepository flagsRepository;
    private final ICooldownService cooldownService;
    private final DataContainer dataContainer = DataContainer.getContainer();

    private static final int
            NIGHT_START_TIME = 12541,
            NIGHT_END_TIME = 23850;

    public SleepService(Sleepmost plugin, IConfigService configService, IConfigRepository configRepository, IMessageService messageService, IFlagsRepository flagsRepository, ICooldownService cooldownService) {
        this.plugin = plugin;
        this.configService = configService;
        this.configRepository = configRepository;
        this.messageService = messageService;
        this.flagsRepository = flagsRepository;
        this.cooldownService = cooldownService;
    }

    @Override
    public boolean enabledForWorld(World world) {
        return configRepository.containsWorld(world);
    }

    @Override
    public boolean sleepPercentageReached(World world) {

        return getPlayersSleepingCount(world) >= getRequiredPlayersSleepingCount(world);
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
        return this.dataContainer.getSleepingPlayers(world).size();
    }

    @Override
    public int getRequiredPlayersSleepingCount(World world) {

        int requiredCount;

        switch (this.flagsRepository.getCalculationMethodFlag().getValueAt(world)) {
            case PERCENTAGE_REQUIRED:
                requiredCount = (int) Math.ceil(getPlayerCountInWorld(world) * getPercentageRequired(world));
                break;
            case PLAYERS_REQUIRED:
                int requiredPlayersInConfig = this.flagsRepository.getPlayersRequiredFlag().getValueAt(world);
                requiredCount = (requiredPlayersInConfig <= getPlayerCountInWorld(world)) ? requiredPlayersInConfig : getPlayerCountInWorld(world);
                break;
            default:
                requiredCount = 0;
        }

        return requiredCount;
    }

    @Override
    public int getPlayerCountInWorld(World world) {

        //full list of players
        List<Player> allPlayers = world.getPlayers();

        //check if exempt flag is enabled
        if (configRepository.getUseExempt(world)) {
            allPlayers = allPlayers.stream()
                    .filter(p -> !p.hasPermission("sleepmost.exempt"))
                    .collect(toList());
        }
        boolean afkFlagEnabled = this.flagsRepository.getUseAfkFlag().getValueAt(world);
        boolean placeHolderAPIEnabled = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        boolean essentialsEnabled = Bukkit.getPluginManager().getPlugin("Essentials") != null;

        //check if user is afk
        if (afkFlagEnabled && placeHolderAPIEnabled && essentialsEnabled)
            allPlayers = allPlayers.stream()
                    .filter(p -> PlaceholderAPI.setPlaceholders(p, "%essentials_afk%").equalsIgnoreCase("no"))
                    .collect(toList());

        return (allPlayers.size() > 0) ? allPlayers.size() : 1;
    }

    @Override
    public void resetDay(World world, String lastSleeperName, String lastSleeperDisplayName) {

        if (!resetRequired(world))
            return;

        if (!sleepPercentageReached(world))
            return;

        int skipDelay = this.flagsRepository.getSkipDelayFlag().getValueAt(world);

        new BukkitRunnable() {
            @Override
            public void run() {
                executeSleepReset(world, lastSleeperName, lastSleeperDisplayName);
            }
        }.runTaskLater(plugin, skipDelay * 20L);
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
    public SleepSkipCause getSleepSkipCause(World world) {
        return isNight(world) ? SleepSkipCause.NIGHT_TIME : SleepSkipCause.STORM;
    }

    @Override
    public void reloadConfig() {
        configRepository.reloadConfig();
    }

    @Override
    public void enableForWorld(World world) {
        configRepository.addWorld(world);
    }

    @Override
    public void disableForWorld(World world) {
        configRepository.disableForWorld(world);
    }

    @Override
    public void setSleeping(Player player, boolean sleeping) {

        this.dataContainer.setPlayerSleeping(player, sleeping);

        // do nothing if skip conditions are not yet met is not required
        if (!this.shouldSkip(player))
            return;

        // trigger the skip event logic
        onSleepSkip(player.getWorld(), player.getName(), player.getDisplayName());
    }

    @Override
    public boolean isPlayerAsleep(Player player) {
        World world = player.getWorld();

        return this.dataContainer.getSleepingPlayers(world).contains(player);
    }

    @Override
    public int getSleepingPlayersAmount(World world) {
        return this.dataContainer.getSleepingPlayers(world).size();
    }

    @Override
    public void onSleepSkip(World world, String lastSleeperName, String lastSleeperDisplayName) {

        boolean withNightcycleAnimation = this.flagsRepository.getNightcycleAnimationFlag().getValueAt(world);

        if (withNightcycleAnimation && isNight(world)) {
            dataContainer.setAnimationRunning(world, true);
            new NightcycleAnimationTask(this, this.messageService, world, lastSleeperName).runTaskTimer(this.plugin, 0, 1);
            return;
        }

        resetDay(world, lastSleeperName, lastSleeperDisplayName);
    }

    private void executeSleepReset(World world, String lastSleeperName, String lastSleeperDisplayName) {

        SleepSkipCause cause = SleepSkipCause.UNKNOWN;

        if (this.isNight(world)) {
            cause = SleepSkipCause.NIGHT_TIME;
            world.setTime(configService.getResetTime());
        } else if (world.isThundering()) {
            cause = SleepSkipCause.STORM;
        }

        world.setThundering(false);
        world.setStorm(false);
        Bukkit.getServer().getPluginManager().callEvent(new SleepSkipEvent(world, cause, lastSleeperName, lastSleeperDisplayName));

    }

    private boolean shouldSkip(Player player) {

        World world = player.getWorld();

        boolean sleepPercentageReached = this.sleepPercentageReached(world);

        if (!this.enabledForWorld(player.getWorld()))
            return false;

        if (!resetRequired(player.getWorld()))
            return false;


        if (this.cooldownService.cooldownEnabled() && !cooldownService.isCoolingDown(player)) {
            messageService.sendPlayerLeftMessage(player, this.getSleepSkipCause(world));
            cooldownService.startCooldown(player);
        }

        return sleepPercentageReached;
    }
}
