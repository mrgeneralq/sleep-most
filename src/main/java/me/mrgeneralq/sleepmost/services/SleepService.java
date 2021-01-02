package me.mrgeneralq.sleepmost.services;
import me.clip.placeholderapi.PlaceholderAPI;
import me.mrgeneralq.sleepmost.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import me.mrgeneralq.sleepmost.statics.SleepFlagMapper;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SleepService implements ISleepService {

    private ISleepFlagService sleepFlagService;
    private final IConfigRepository configRepository;
    private final IConfigService configService;

    public SleepService(IConfigService configService, ISleepFlagService sleepFlagService, IConfigRepository configRepository){
        this.configService = configService;
        this.sleepFlagService = sleepFlagService;
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

        List<Player> sleepingPlayers = new ArrayList<Player>();

        //check for lower versions
        if(ServerVersion.CURRENT_VERSION.sleepCalculatedDifferently()){
            sleepingPlayers = DataContainer.getContainer().getSleepingPlayers(world);
            return (int) sleepingPlayers.size();
        }
        else{
            sleepingPlayers = world.getPlayers().stream().filter(Player::isSleeping).collect(Collectors.toList());
            return (int) sleepingPlayers.size() + 1;
        }

    }

    @Override
    public int getRequiredPlayersSleepingCount(World world) {


        SleepCalculationType sleepCalculationType = SleepCalculationType.PERCENTAGE_REQUIRED;
        int requiredCount = 0;

        try{

            String enumName = String.format("%s%s", (String) sleepFlagService.getFlagValue(world, "calculation-method"),"_REQUIRED");
            enumName = enumName.toUpperCase();

            sleepCalculationType = SleepCalculationType.valueOf(enumName);
        }catch (Exception ex){}

        switch (sleepCalculationType){
            case PERCENTAGE_REQUIRED:
                requiredCount =  (int) Math.ceil(getPlayerCountInWorld(world) * getPercentageRequired(world));
                break;
            case PLAYERS_REQUIRED:

                int requiredPlayersInconfig = (int) sleepFlagService.getFlagValue(world,"players-required");

                requiredCount = (requiredPlayersInconfig <= getPlayerCountInWorld(world)) ? requiredPlayersInconfig: getPlayerCountInWorld(world);
        }

        return requiredCount;
    }

    @Override
    public int getPlayerCountInWorld(World world) {

        //full list of players
        List<Player> allPlayers = world.getPlayers();


        //check if exempt flag is enabled
        if(configRepository.getUseExempt(world)){
            allPlayers = allPlayers.stream().filter(p -> !p.hasPermission("sleepmost.exempt")).collect(Collectors.toList());
        }


        ISleepFlag flag = SleepFlagMapper.getMapper().getFlag("use-afk");
        boolean afkFlagEnabled = (boolean) flag.getValue(world);

        //check if user is afk
           if (afkFlagEnabled && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && Bukkit.getPluginManager().getPlugin("Essentials") != null)
               allPlayers = allPlayers.stream().filter(p -> PlaceholderAPI.setPlaceholders(p, "%essentials_afk%").equalsIgnoreCase("no")).collect(toList());

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

    @Override
    public void setFlag(World world, ISleepFlag<?> flag, String value) {

        Object convertedFlagValue = null;

        switch(flag.getFlagType())
        {
        case BOOLEAN:
        	 convertedFlagValue = Boolean.parseBoolean(value);
        	 break;
        case DOUBLE:
        	convertedFlagValue = Double.parseDouble(value);
        	break;
        case STRING:
        	convertedFlagValue = value;
        	break;
        case INTEGER:
        	convertedFlagValue = Integer.parseInt(value);
        	break;
        }
        configRepository.setFlag(world, flag.getFlagName(), convertedFlagValue);
    }
}
