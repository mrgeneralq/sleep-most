package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IInsomniaService;
import me.mrgeneralq.sleepmost.interfaces.ISleepMostPlayerService;
import org.bukkit.World;
import org.bukkit.entity.Player;

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
        this.sleepMostPlayerService.getPlayer(player).setInsomniaStatus(world, false);
    }

    @Override
    public void disableInsomnia(World world) {
        for(Player p: world.getPlayers())
            this.disableInsomnia(p, world);
    }
}
