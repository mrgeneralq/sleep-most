package me.qintinator.sleepmost.eventlisteners;

import me.qintinator.sleepmost.interfaces.IUpdateService;
import me.qintinator.sleepmost.statics.Message;
import me.qintinator.sleepmost.statics.VersionController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoinEventListener implements Listener {

    private final IUpdateService updateService;
    private final Plugin plugin;

    public PlayerJoinEventListener(Plugin plugin, IUpdateService updateService) {
        this.plugin = plugin;
        this.updateService = updateService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        if (player.hasPermission("sleepmost.alerts"))
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                notifyNewUpdate(player);
            });
    }

    //Notify the player if this plugin has a new update which the server doesn't have
    private void notifyNewUpdate(Player player) {
        if (updateService.hasUpdate()){
            player.sendMessage(Message.colorize("&bA newer version of &esleep-most &bis available! &cPlease note that support is only given to the latest version"));
            player.sendMessage(Message.colorize(String.format("&a%s", VersionController.UPDATE_URL)));
        }
    }
}
