package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.shared.versioning.ServerVersion;
import me.mrgeneralq.sleepmost.services.IBossBarService;
import me.mrgeneralq.sleepmost.services.IMessageService;
import me.mrgeneralq.sleepmost.services.ISleepMostPlayerService;
import me.mrgeneralq.sleepmost.services.IUpdateService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import static me.mrgeneralq.shared.messaging.ChatColorUtils.colorize;

public class PlayerJoinEventListener implements Listener {

    private final IMessageService messageService;
    private final IBossBarService bossBarService;
    private final ISleepMostPlayerService sleepMostPlayerService;
    private final IUpdateService updateService;
    private final Plugin plugin;

    private static final String UPDATE_PERMISSION = "sleepmost.alerts.update";

    public PlayerJoinEventListener(Plugin plugin, IUpdateService updateService, IMessageService messageService, IBossBarService bossBarService, ISleepMostPlayerService sleepMostPlayerService) {
        this.plugin = plugin;
        this.updateService = updateService;
        this.messageService = messageService;
        this.bossBarService = bossBarService;
        this.sleepMostPlayerService = sleepMostPlayerService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        //if player does not exist yet, register. Avoid players disconnecting and reconnecting to reset their profile
        if(!this.sleepMostPlayerService.playerExists(player))
        this.sleepMostPlayerService.registerNewPlayer(player);

        if(ServerVersion.CURRENT_VERSION.supportsBossBars())
        this.bossBarService.registerPlayer(player.getWorld(), player);

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
        sender.sendMessage(messageService.getMessagePrefixed("&bA newer version of &esleep-most &bis available: &e%updateLink%")
                .setPlaceHolder("%updateLink%", updateService.getCachedUpdateVersion())
                .build());
        sender.sendMessage(ChatColor.GREEN + ServerVersion.UPDATE_URL);
        sender.sendMessage(colorize("&eYou may ignore this message if you just updated (spigot takes some time)"));
        sender.sendMessage(colorize("&b==============================================="));
    }
}
