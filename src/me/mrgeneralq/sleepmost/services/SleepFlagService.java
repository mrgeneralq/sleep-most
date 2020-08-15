package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.statics.SleepFlagMapper;
import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

public class SleepFlagService implements ISleepFlagService {

    public final IConfigRepository configRepository;

    public SleepFlagService(IConfigRepository configRepository){
        this.configRepository = configRepository;
    }


    @Override
    public void setFlag(World world, String flagName, Object value) {
        configRepository.setFlag(world, flagName, value);
    }

    public Object getFlag(World world, String flagName){
        return configRepository.getFlag(world, flagName);
    }

    @Override
    public ISleepFlag getSleepFlag(String flagName) {
        return SleepFlagMapper.getMapper().getFlag(flagName);
    }

}
