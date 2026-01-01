package me.mrgeneralq.sleepmost.core.interfaces;

import org.bukkit.World;
import org.bukkit.entity.Player;

public interface IInsomniaService {

    void enableInsomnia(Player player, World world);
    void enableInsomnia(World world);
    void disableInsomnia(Player player, World world);
    void disableInsomnia(World world);

    boolean hasInsomniaEnabled(Player player);
}
