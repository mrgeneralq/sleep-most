package me.mrgeneralq.sleepmost.services;

import org.bukkit.entity.Player;

public interface IDebugService {
    void enableFor(Player player);
    void disableFor(Player player);
    boolean isEnabledFor(Player player);
    void print(String logMsg);
}
