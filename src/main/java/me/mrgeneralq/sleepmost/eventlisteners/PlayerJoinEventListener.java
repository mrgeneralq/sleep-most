package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
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

        if (!player.hasPermission("sleepmost.alerts"))
            return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
        {
            if(updateService.hasUpdate())
                notifyNewUpdate(player);
        });
    }
    private void notifyNewUpdate(CommandSender sender) {
        sender.sendMessage(colorize("&b==============================================="));
        sender.sendMessage(colorize(String.format("&bA newer version of &esleep-most &bis available: &e%s", updateService.getCachedUpdateVersion())));
        sender.sendMessage(ChatColor.GREEN + ServerVersion.UPDATE_URL);
        sender.sendMessage(colorize("&eYou may ignore this message if you just updated (spigot takes some time)"));
        sender.sendMessage(colorize("&b==============================================="));
    }
}
