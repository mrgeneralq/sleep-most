package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.IMessageService;
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

    private final IMessageService messageService;
    private final IUpdateService updateService;
    private final Plugin plugin;

    private static final String UPDATE_PERMISSION = "sleepmost.alerts.update";

    public PlayerJoinEventListener(Plugin plugin, IUpdateService updateService, IMessageService messageService) {
        this.plugin = plugin;
        this.updateService = updateService;
        this.messageService = messageService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        if (!player.hasPermission(UPDATE_PERMISSION))
            return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
        {
            if(updateService.hasUpdate())
                notifyNewUpdate(player);
        });
    }
    private void notifyNewUpdate(CommandSender sender) {
        sender.sendMessage(colorize("&b==============================================="));
        sender.sendMessage(messageService.newBuilder("&bA newer version of &esleep-most &bis available: &e%updateLink%")
                .setPlaceHolder("%updateLink%", updateService.getCachedUpdateVersion())
                .build());
        sender.sendMessage(ChatColor.GREEN + ServerVersion.UPDATE_URL);
        sender.sendMessage(colorize("&eYou may ignore this message if you just updated (spigot takes some time)"));
        sender.sendMessage(colorize("&b==============================================="));
    }
}
