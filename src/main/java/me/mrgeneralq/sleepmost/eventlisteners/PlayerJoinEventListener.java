package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import static me.mrgeneralq.sleepmost.statics.ChatColorUtils.colorize;

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

            player.sendMessage(colorize("&b==============================================="));
            player.sendMessage(colorize(String.format("&bA newer version of &esleep-most &bis available: &e%s", updateService.getCachedUpdateVersion())));
            player.sendMessage(ChatColor.GREEN + ServerVersion.UPDATE_URL);
            player.sendMessage(colorize("&eYou may ignore this message if you just updated (spigot takes some time)"));
            player.sendMessage(colorize("&b==============================================="));

        }
    }
}
