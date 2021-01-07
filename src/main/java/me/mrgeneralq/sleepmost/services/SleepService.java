package me.mrgeneralq.sleepmost.services;
import me.clip.placeholderapi.PlaceholderAPI;
import me.mrgeneralq.sleepmost.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import me.mrgeneralq.sleepmost.repositories.FlagsRepository;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static java.util.stream.Collectors.toList;

import java.util.List;

public class SleepService implements ISleepService {

    private final IConfigRepository configRepository;
    private final IConfigService configService;
    private final FlagsRepository flagsRepository = FlagsRepository.getInstance();

    public SleepService(IConfigService configService, IConfigRepository configRepository){
        this.configService = configService;
        this.configRepository = configRepository;
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
        if(ServerVersion.CURRENT_VERSION.sleepCalculatedDifferently()){
            return DataContainer.getContainer().getSleepingPlayers(world).size();
        }
        else {
            return (int) (world.getPlayers().stream().filter(Player::isSleeping).count()+1);
        }

    }

    @Override
    public int getRequiredPlayersSleepingCount(World world) {


        SleepCalculationType sleepCalculationType = SleepCalculationType.PERCENTAGE_REQUIRED;

        int requiredCount = 0;

        try{
            String enumName = String.format("%s%s", this.flagsRepository.getCalculationMethodFlag().getController().getValueAt(world) ,"_REQUIRED");
            enumName = enumName.toUpperCase();

            sleepCalculationType = SleepCalculationType.valueOf(enumName);
        }catch (Exception ex){}

        switch (sleepCalculationType){
            case PERCENTAGE_REQUIRED:
                requiredCount =  (int) Math.ceil(getPlayerCountInWorld(world) * getPercentageRequired(world));
                break;
            case PLAYERS_REQUIRED:
                int requiredPlayersInConfig = this.flagsRepository.getPlayersRequiredFlag().getController().getValueAt(world);

                requiredCount = (requiredPlayersInConfig <= getPlayerCountInWorld(world)) ? requiredPlayersInConfig: getPlayerCountInWorld(world);
        }

        return requiredCount;
    }

    @Override
    public int getPlayerCountInWorld(World world) {

        //full list of players
        List<Player> allPlayers = world.getPlayers();

        //check if exempt flag is enabled
        if(configRepository.getUseExempt(world)){
            allPlayers = allPlayers.stream()
                    .filter(p -> !p.hasPermission("sleepmost.exempt"))
                    .collect(toList());
        }
        boolean afkFlagEnabled = this.flagsRepository.getUseAfkFlag().getController().getValueAt(world);

        //check if user is afk
        if(afkFlagEnabled && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && Bukkit.getPluginManager().getPlugin("Essentials") != null)
               allPlayers = allPlayers.stream()
                       .filter(p -> PlaceholderAPI.setPlaceholders(p, "%essentials_afk%").equalsIgnoreCase("no"))
                       .collect(toList());

            return (allPlayers.size() > 0) ? allPlayers.size() : 1;
    }

    @Override
    public void resetDay(World world, String lastSleeperName, String lastSleeperDisplayName) {
        SleepSkipCause cause = SleepSkipCause.UNKNOWN;

        if (this.isNight(world)) {
            cause = SleepSkipCause.NIGHT_TIME;
            world.setTime(configService.getResetTime());
        } else if(world.isThundering()){
            cause = SleepSkipCause.STORM;
        }
        
        world.setThundering(false);
        world.setStorm(false);
        Bukkit.getServer().getPluginManager().callEvent(new SleepSkipEvent(world, cause, lastSleeperName, lastSleeperDisplayName));
    }

    @Override
    public boolean resetRequired(World world) {
    	return isNight(world) || world.isThundering();
    }

    @Override
    public boolean isNight(World world) {
            return (world.getTime() > 12541 && world.getTime() < 23850);
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
}
