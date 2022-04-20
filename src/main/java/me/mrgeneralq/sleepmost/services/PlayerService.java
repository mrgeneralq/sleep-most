package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IPlayerService;
import me.mrgeneralq.sleepmost.utils.PlayerUtils;
import org.bukkit.entity.Player;

public class PlayerService implements IPlayerService {

    @Override
    public boolean isRealPlayer(Player player) {
        return PlayerUtils.isRealPlayer(player);
    }
}
