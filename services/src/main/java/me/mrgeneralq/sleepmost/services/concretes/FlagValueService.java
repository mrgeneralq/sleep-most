package me.mrgeneralq.sleepmost.services.concretes;

import me.mrgeneralq.sleepmost.models.SleepMostFlag;
import me.mrgeneralq.sleepmost.services.IFlagValueService;
import org.bukkit.World;

public class FlagValueService implements IFlagValueService {


    public FlagValueService() {
    }

    @Override
    public <T> T getValueAt(SleepMostFlag<T> flag, World world) {
        return null;
    }
}
