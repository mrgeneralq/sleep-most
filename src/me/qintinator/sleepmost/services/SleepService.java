package me.qintinator.sleepmost.services;
import me.clip.placeholderapi.PlaceholderAPI;
import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.enums.SleepSkipCause;
import me.qintinator.sleepmost.events.SleepSkipEvent;
import me.qintinator.sleepmost.interfaces.*;
import me.qintinator.sleepmost.statics.DataContainer;
import me.qintinator.sleepmost.statics.SleepFlagMapper;
import me.qintinator.sleepmost.statics.VersionController;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

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
        if(VersionController.isOldVersion()){
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
        return (int) Math.ceil(getPlayerCountInWorld(world) * getPercentageRequired(world));
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
               allPlayers = allPlayers.stream().filter(p -> PlaceholderAPI.setPlaceholders(p, "%essentials_afk%").equalsIgnoreCase("no")).collect(Collectors.toList());

            return (allPlayers.size() > 0) ? allPlayers.size() : 1;
    }

    @Override
    public void resetDay(World world) {
        SleepSkipCause cause;

        if (this.isNight(world)) {
            cause = SleepSkipCause.NIGHT_TIME;
            world.setTime(configService.getResetTime());
        } else {
            cause = SleepSkipCause.STORM;
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
            return SleepSkipCause.NIGHT_TIME;
        return SleepSkipCause.STORM;
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
