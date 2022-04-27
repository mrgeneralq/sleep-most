package me.mrgeneralq.sleepmost.services;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.enums.SleepState;
import me.mrgeneralq.sleepmost.events.PlayerSleepStateChangeEvent;
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
    private final IPlayerService playerService;
    private final DataContainer dataContainer = DataContainer.getContainer();

    private static final int
            NIGHT_START_TIME = 12541,
            NIGHT_END_TIME = 23850;

    public SleepService(
            Sleepmost main,
            IConfigService configService,
            IConfigRepository configRepository,
            IFlagsRepository flagsRepository,
            IFlagService flagService,
            IPlayerService playerService
    ) {

        this.main = main;
        this.configService = configService;
        this.configRepository = configRepository;
        this.flagsRepository = flagsRepository;
        this.flagService = flagService;
        this.playerService = playerService;
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

        //exclude fake players
        playersStream = playersStream.filter(this.playerService::isRealPlayer);

        // If flag is active, ignore players in spectator mode from sleep count.
        if (flagsRepository.getExemptSpectatorFlag().getValueAt(world))
            playersStream = playersStream.filter(p -> p.getGameMode() != GameMode.SPECTATOR);

        // If flag is active, ignore players in creative mode from sleep count.
        if(flagsRepository.getExemptCreativeFlag().getValueAt(world))
            playersStream = playersStream.filter(p -> p.getGameMode() != GameMode.CREATIVE);

        if(flagsRepository.getUseExemptFlag().getValueAt(world))
            playersStream = playersStream.filter(p -> !p.hasPermission("sleepmost.exempt"));

        if(flagsRepository.getExemptFlyingFlag().getValueAt(world))
            playersStream = playersStream.filter(p -> !p.isFlying());

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
        return (double)getSleepersAmount(world) / (double)getRequiredSleepersCount(world);
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

        SleepState sleepState = (sleeping) ? SleepState.SLEEPING: SleepState.AWAKE;

        PlayerSleepStateChangeEvent sleepStateChangeEvent = new PlayerSleepStateChangeEvent(player, sleepState);
        this.dataContainer.setPlayerSleeping(player, sleeping);

        Bukkit.getPluginManager().callEvent(sleepStateChangeEvent);

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

    @Override
    public boolean shouldSkip(World world) {
        return isEnabledAt(world) &&
                resetRequired(world) &&
                isRequiredCountReached(world);
    }

    @Override
    public void runSkipAnimation(Player player, SleepSkipCause sleepSkipCause) {
        World world = player.getWorld();

        if(this.dataContainer.isAnimationRunningAt(world))
            return;

        List<OfflinePlayer> sleepingPlayers = this.getSleepers(world).stream().map(p -> Bukkit.getOfflinePlayer(p.getUniqueId())).collect(Collectors.toList());

        dataContainer.setAnimationRunning(world, true);
        new NightcycleAnimationTask(this, this.flagsRepository , world, player, sleepingPlayers , sleepSkipCause).runTaskTimer(this.main, 0, 1);
    }


    private Stream<Player> getRealPlayers(World world){
        return world.getPlayers().stream().filter(p -> Bukkit.getPlayer(p.getUniqueId()) != null);
    }
}