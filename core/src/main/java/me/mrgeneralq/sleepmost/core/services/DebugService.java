package me.mrgeneralq.sleepmost.core.services;

import me.mrgeneralq.sleepmost.core.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.core.interfaces.IDebugService;
import me.mrgeneralq.sleepmost.core.interfaces.ISleepMostPlayerService;
import me.mrgeneralq.sleepmost.core.models.SleepMostPlayer;
import me.mrgeneralq.sleepmost.core.statics.ChatColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class DebugService implements IDebugService {

    private final ISleepMostPlayerService sleepMostPlayerService;
    private final IConfigService configService;

    public DebugService(ISleepMostPlayerService sleepMostPlayerService, IConfigService configService) {
        this.sleepMostPlayerService = sleepMostPlayerService;
        this.configService = configService;
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
                .filter(p -> p.getPlayerUUID().isOnline())
                .collect(Collectors.toList())){

            p.getPlayerUUID().getPlayer().sendMessage(ChatColorUtils.colorize(logMsg));

        }

        if(this.configService.debugModeEnabled())
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColorUtils.colorize(logMsg));
    }
}
