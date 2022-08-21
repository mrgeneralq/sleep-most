package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IDebugService;
import me.mrgeneralq.sleepmost.interfaces.ISleepMostPlayerService;
import me.mrgeneralq.sleepmost.models.SleepMostPlayer;
import me.mrgeneralq.sleepmost.statics.ChatColorUtils;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class DebugService implements IDebugService {

    private final ISleepMostPlayerService sleepMostPlayerService;

    public DebugService(ISleepMostPlayerService sleepMostPlayerService) {
        this.sleepMostPlayerService = sleepMostPlayerService;
    }

    @Override
    public void enableFor(Player player) {
        this.sleepMostPlayerService.getPlayer(player).setDebugMode(true);
    }

    @Override
    public void disableFor(Player player) {
        this.sleepMostPlayerService.getPlayer(player).setDebugMode(false);
    }

    @Override
    public boolean isEnabledFor(Player player){
        return this.sleepMostPlayerService.getPlayer(player).hasDebugMode();
    }

    @Override
    public void print(String logMsg) {

        for(SleepMostPlayer p: this.sleepMostPlayerService.getAllPlayers()
                .stream()
                .filter(SleepMostPlayer::hasDebugMode)
                .filter(p -> p.getPlayer().isOnline())
                .collect(Collectors.toList())){

            p.getPlayer().getPlayer().sendMessage(ChatColorUtils.colorize(logMsg));

        }
    }
}
