package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IInsomniaService;
import me.mrgeneralq.sleepmost.interfaces.ISleepMostPlayerService;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class InsomniaService implements IInsomniaService {

    private final ISleepMostPlayerService sleepMostPlayerService;

    public InsomniaService(ISleepMostPlayerService sleepMostPlayerService) {
        this.sleepMostPlayerService = sleepMostPlayerService;
    }

    @Override
    public void enableInsomnia(Player player, World world) {
        this.sleepMostPlayerService.getPlayer(player).setInsomniaStatus(world, true);
    }

    @Override
    public void enableInsomnia(World world) {
        for(Player p: world.getPlayers())
            this.enableInsomnia(p, world);
    }

    @Override
    public void disableInsomnia(Player player, World world) {

        if(!player.isOnline())
            return;

        this.sleepMostPlayerService.getPlayer(player).setInsomniaStatus(world, false);
    }

    @Override
    public void disableInsomnia(World world) {
        for(Player p: world.getPlayers().stream().filter(OfflinePlayer::isOnline).collect(Collectors.toList()))
            this.disableInsomnia(p, world);
    }

    @Override
    public boolean hasInsomniaEnabled(Player player){
        return this.sleepMostPlayerService.getPlayer(player).getInsomniaStatus(player.getWorld());
    }
}
