package me.qintinator.sleepmost.eventlisteners;

import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.services.SleepService;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class OnPlayerWorldChange implements Listener {

    private final ISleepService sleepService;

    public OnPlayerWorldChange(ISleepService sleepService){
        this.sleepService = sleepService;
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent e){
        Player player  = e.getPlayer();
        World world = player.getWorld();

        if(!player.hasPermission("sleepmost.notify"))
            return;

        if(sleepService.enabledForWorld(world))
            return;

        player.sendMessage(Message.currentlyDisabled);

    }
}
