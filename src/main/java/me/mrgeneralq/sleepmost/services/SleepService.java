package me.mrgeneralq.sleepmost.services;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.flags.CalculationMethodFlag;
import me.mrgeneralq.sleepmost.flags.PlayersRequiredFlag;
import me.mrgeneralq.sleepmost.flags.SkipDelayFlag;
import me.mrgeneralq.sleepmost.flags.UseAfkFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import static java.util.stream.Collectors.toList;

import java.util.List;

public class SleepService implements ISleepService {

    private final IConfigRepository configRepository;
    private final IConfigService configService;
    private final SkipDelayFlag skipDelayFlag;


    private final CalculationMethodFlag calculationMethodFlag;
    private final PlayersRequiredFlag playersRequiredFlag;
    private final UseAfkFlag useAfkFlag;
    private final Sleepmost plugin;

    private static final int
            NIGHT_START_TIME = 12541,
            NIGHT_END_TIME = 23850;

    public SleepService(
            Sleepmost plugin,
            IConfigService configService,
            IConfigRepository configRepository,
            CalculationMethodFlag calculationMethodFlag,
            PlayersRequiredFlag playersRequiredFlag,
            UseAfkFlag useAfkFlag,
            SkipDelayFlag skipDelayFlag)
    {

        this.plugin = plugin;
        this.configService = configService;
        this.configRepository = configRepository;

        this.calculationMethodFlag = calculationMethodFlag;
        this.playersRequiredFlag = playersRequiredFlag;
        this.useAfkFlag = useAfkFlag;
        this.skipDelayFlag = skipDelayFlag;
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
        if (ServerVersion.CURRENT_VERSION.sleepCalculatedDifferently()) {
            return DataContainer.getContainer().getSleepingPlayers(world).size();
        } else {
            return (int) (world.getPlayers().stream().filter(Player::isSleeping).count() + 1);
        }

    }

    @Override
    public int getRequiredPlayersSleepingCount(World world) {

        int requiredCount;

        switch (this.calculationMethodFlag.getValueAt(world)) {
            case PERCENTAGE_REQUIRED:
                requiredCount = (int) Math.ceil(getPlayerCountInWorld(world) * getPercentageRequired(world));
                break;
            case PLAYERS_REQUIRED:
                int requiredPlayersInConfig = this.playersRequiredFlag.getValueAt(world);
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
        boolean afkFlagEnabled = this.useAfkFlag.getValueAt(world);

        //check if user is afk
        if (afkFlagEnabled && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && Bukkit.getPluginManager().getPlugin("Essentials") != null)
            allPlayers = allPlayers.stream()
                    .filter(p -> PlaceholderAPI.setPlaceholders(p, "%essentials_afk%").equalsIgnoreCase("no"))
                    .collect(toList());

        return (allPlayers.size() > 0) ? allPlayers.size() : 1;
    }

    @Override
    public void resetDay(World world, String lastSleeperName, String lastSleeperDisplayName) {

        /*here we need to add a scheduler that runs all of this code after if the value of the skip-delay flag is higher then 0. If it is,
         * we run the code to skip the night and reset the time. ALERT: we need to check again in the condition if the required players are asleep, if so we skip.
         * we also need to check if time is not already skipped, so if time = day, do not run code (return)
         */

        int skipDelay = skipDelayFlag.getValueAt(world);

        new BukkitRunnable() {
            @Override
            public void run() {

                if (!resetRequired(world))
                    return;

                if (!sleepPercentageReached(world))
                    return;

                executeSleepReset(world, lastSleeperName, lastSleeperDisplayName);

            }
        }.runTaskLaterAsynchronously(plugin, skipDelay * 20L);
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


    // this method will invoke the reset functionality for the day
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
}
