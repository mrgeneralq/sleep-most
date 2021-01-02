package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.enums.SleepmostFlag;
import me.mrgeneralq.sleepmost.mappers.SleepFlagMapper;
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

    public Object getFlagValue(World world, String flagName){
        return configRepository.getFlag(world, flagName);
    }

    @Override
    public ISleepFlag<?> getFlag(SleepmostFlag flag, World world) {
        ISleepFlag<?> newFlag = SleepFlagMapper.getMapper().getFlag(flag);
        String flagPath = String.format("%s.%s", world.getName(), newFlag.getFlagName());

    }

    @Override
    public ISleepFlag getSleepFlag(String flagName) {

        //here comes the magic

        return SleepFlagMapper.getMapper().getFlag(flagName);
    }

}
