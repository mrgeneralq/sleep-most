package me.mrgeneralq.sleepmost.eventlisteners;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import me.mrgeneralq.sleepmost.statics.Message;
import me.mrgeneralq.sleepmost.statics.VersionController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.plugin.Plugin;

public class TimeSkipEventListener implements Listener {

    @EventHandler
    public void onTimeSkip(TimeSkipEvent e) {
        TimeSkipEvent.SkipReason reason = e.getSkipReason();
        if (reason == TimeSkipEvent.SkipReason.NIGHT_SKIP)
            e.setCancelled(true);
    }
}
