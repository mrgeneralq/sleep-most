package me.mrgeneralq.sleepmost.core.services;

import me.mrgeneralq.sleepmost.core.interfaces.IPlayerService;
import me.mrgeneralq.sleepmost.core.utils.PlayerUtils;
import org.bukkit.entity.Player;

public class PlayerService implements IPlayerService {

    @Override
    public boolean isRealPlayer(Player player) {
        return PlayerUtils.isRealPlayer(player);
    }
}
