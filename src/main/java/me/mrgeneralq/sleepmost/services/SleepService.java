package me.mrgeneralq.sleepmost.services;

import de.myzelyam.api.vanish.VanishAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.enums.HookType;
import me.mrgeneralq.sleepmost.enums.SleepState;
import me.mrgeneralq.sleepmost.events.PlayerSleepStateChangeEvent;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.runnables.NightcycleAnimationTask;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
//import org.bukkit.entity.Player;

import static me.mrgeneralq.sleepmost.enums.SleepSkipCause.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private final IDebugService debugService;
    private final DataContainer dataContainer = DataContainer.getContainer();
    private final ISleepMostWorldService sleepMostWorldService;
    private final IHookService hookService;
    private final IMessageService messageService;

    private static final int
            NIGHT_START_TIME = 12541,
            NIGHT_END_TIME = 23460;

    public SleepService(
            Sleepmost main,
            IConfigService configService,
            IConfigRepository configRepository,
            IFlagsRepository flagsRepository,
            IFlagService flagService,
            IPlayerService playerService,
            IDebugService debugService,
            ISleepMostWorldService sleepMostWorldService,
            IHookService hookService,
            IMessageService messageService
    ) {

        this.main = main;
        this.configService = configService;
        this.configRepository = configRepository;
        this.flagsRepository = flagsRepository;
        this.flagService = flagService;
        this.playerService = playerService;
        this.debugService = debugService;
        this.sleepMostWorldService = sleepMostWorldService;
        this.hookService = hookService;
        this.messageService = messageService;
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


        List<Player> playersList = world.getPlayers();
        List<Player> newPlayerList = playersList;

        this.debugService.print("");
        this.debugService.print("=============================");
        this.debugService.print(String.format("&f[&b%s&f] &fTotal players [&e%s&f]: %s", world.getName(), playersList.size() , getJoinedStream(playersList, newPlayerList)));


        //AFK DETECTION (reflection)
        if(this.flagsRepository.getUseAfkFlag().getValueAt(world)){

        try{
            Method isAfkMethod = Player.class.getMethod("isAfk");

            newPlayerList = playersList.stream().filter(p -> {
                try {
                    return (!(boolean) p.getClass().getMethod("isAfk").invoke(p));
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
                    throw new RuntimeException(ignored);
                }
            }).collect(Collectors.toList());
            this.debugService.print(String.format("&f[&b%s&f] &fNon AFK (server) [&e%s&f]: %s", world.getName() , playersList.size() , getJoinedStream(playersList, newPlayerList)));
            playersList = newPlayerList;
        } catch (NoSuchMethodException ignored) {}

        }
        

        if(hookService.isRegistered(HookType.SUPER_VANISH)){
            newPlayerList = playersList.stream().filter(p -> !VanishAPI.isInvisible(p)).collect(Collectors.toList());
            this.debugService.print(String.format("&f[&b%s&f] &fVisible players (super vanish) [&e%s&f]: %s", world.getName() , playersList.size() , getJoinedStream(playersList, newPlayerList)));
            playersList = newPlayerList;
        }

        //exclude fake players
        newPlayerList = playersList.stream().filter(this.playerService::isRealPlayer).collect(Collectors.toList());
        this.debugService.print(String.format("&f[&b%s&f] &fReal players [&e%s&f]: %s", world.getName() , playersList.size() , getJoinedStream(playersList, newPlayerList)));
        playersList = newPlayerList;

        // If flag is active, ignore players in spectator mode from sleep count.
        if (flagsRepository.getExemptSpectatorFlag().getValueAt(world)){
            newPlayerList = playersList.stream().filter(p -> p.getGameMode() != GameMode.SPECTATOR).collect(Collectors.toList());
            this.debugService.print(String.format("&f[&b%s&f] &fNon-spectator players [&e%s&f]: %s", world.getName() , playersList.size() , getJoinedStream(playersList, newPlayerList)));
            playersList = newPlayerList;
        }


        // If flag is active, ignore players in creative mode from sleep count.
        if(flagsRepository.getExemptCreativeFlag().getValueAt(world)){
            newPlayerList = playersList.stream().filter(p -> p.getGameMode() != GameMode.CREATIVE).collect(Collectors.toList());
            this.debugService.print(String.format("&f[&b%s&f] &fNon-creative players [&e%s&f]: %s", world.getName() , playersList.size() , getJoinedStream(playersList, newPlayerList)));
            playersList = newPlayerList;
        }


        if(flagsRepository.getUseExemptFlag().getValueAt(world)){
            newPlayerList = playersList.stream().filter(p -> !p.hasPermission("sleepmost.exempt")).collect(Collectors.toList());
            this.debugService.print(String.format("&f[&b%s&f] &fMissing bypass permission (sleepmost.exempt) players [&e%s&f]: %s", world.getName() , playersList.size() , getJoinedStream(playersList, newPlayerList)));
            playersList = newPlayerList;
        }

        if(flagsRepository.getExemptFlyingFlag().getValueAt(world)){
            newPlayerList = playersList.stream().filter(p -> !p.isFlying()).collect(Collectors.toList());
            this.debugService.print(String.format("&f[&b%s&f] &fNon-flying players [&e%s&f]: %s", world.getName() , playersList.size() , getJoinedStream(playersList, newPlayerList)));
            playersList = newPlayerList;
        }

         if (flagService.isAfkFlagUsable() && flagsRepository.getUseAfkFlag().getValueAt(world)){
             newPlayerList = playersList.stream().filter(p -> PlaceholderAPI.setPlaceholders(p, "%essentials_afk%").equalsIgnoreCase("no")).collect(Collectors.toList());
             this.debugService.print(String.format("&f[&b%s&f] &fNon-AFK players [&e%s&f]: %s", world.getName(), playersList.size() , getJoinedStream(playersList, newPlayerList)));
             playersList = newPlayerList;
         }

        int belowYFlagValue = flagsRepository.getExemptBelowYFlag().getValueAt(world);
         if(belowYFlagValue > -1){
             newPlayerList = playersList.stream().filter(p -> p.getLocation().getY() > belowYFlagValue).collect(Collectors.toList());
             this.debugService.print(String.format("&f[&b%s&f] &fPlayers above &b%s&fY-coord [&e%s&f]: %s", world.getName() , belowYFlagValue, playersList.size() , getJoinedStream(playersList, newPlayerList)));
             playersList = newPlayerList;
         }

         int playerCount = (int) playersList.size();
         int endResult = playerCount == 0? 1: playerCount;

        this.debugService.print(String.format("&f[&b%s&f] &fCalculated end TOTAL [&e%s&f]: %s",  world.getName() , endResult , getJoinedStream(playersList, newPlayerList)));
        this.debugService.print("=============================");
        this.debugService.print("");

        return endResult;
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
    public boolean isSleepingPossible(World world) {
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
                isSleepingPossible(world) &&
                isRequiredCountReached(world);
    }

    @Override
    public void runSkipAnimation(Player player, SleepSkipCause sleepSkipCause) {
        World world = player.getWorld();

        if(this.dataContainer.isAnimationRunningAt(world))
            return;

        List<OfflinePlayer> sleepingPlayers = this.getSleepers(world).stream().map(p -> Bukkit.getOfflinePlayer(p.getUniqueId())).collect(Collectors.toList());

        SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);
        sleepMostWorld.setTimeCycleAnimationIsRunning(true);

        new NightcycleAnimationTask(this, this.flagsRepository, world, player, sleepingPlayers , sleepSkipCause, sleepMostWorldService, messageService, configService).runTaskTimer(this.main, 0, 1);
    }


    private Stream<Player> getRealPlayers(World world){
        return world.getPlayers().stream().filter(p -> Bukkit.getPlayer(p.getUniqueId()) != null);
    }

    private String getJoinedStream(List<Player> playersList, List<Player> newPlayersList){

        List<String> names = new ArrayList<>();

        for(Player previousPlayer: playersList){
            if(newPlayersList.contains(previousPlayer))
                names.add(ChatColor.GREEN + previousPlayer.getName() + ChatColor.RESET);
            else
                names.add(ChatColor.RED + previousPlayer.getName() + ChatColor.RESET);

        }

        return String.join(",", names);
    }
}