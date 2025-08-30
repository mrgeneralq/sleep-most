package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.models.SleepMostFlag;
import org.bukkit.World;

public interface IFlagValueService {

    <T> T getValueAt(SleepMostFlag<T> flag, World world);

}
