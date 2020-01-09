package me.qintinator.sleepmost.eventlisteners;
import me.qintinator.sleepmost.interfaces.IUpdateService;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {

    private final IUpdateService updateService;


    public OnPlayerJoin(IUpdateService updateService) {
        this.updateService = updateService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();

        if(!player.hasPermission("sleepmost.alerts"))
            return;

        if(!updateService.hasUpdate())
            return;

        player.sendMessage(Message.getMessage("&3A newer version of sleep-most is available!"));
    }
}
