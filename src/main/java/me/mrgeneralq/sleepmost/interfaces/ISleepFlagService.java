package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.enums.SleepmostFlag;
import org.bukkit.World;

public interface ISleepFlagService {
    ISleepFlag<?> getFlag(SleepmostFlag flag);
}
