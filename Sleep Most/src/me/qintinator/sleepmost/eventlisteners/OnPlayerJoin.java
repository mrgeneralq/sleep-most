package me.qintinator.sleepmost.eventlisteners;
import me.qintinator.sleepmost.interfaces.IUpdateService;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.Bukkit;
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

        Runnable updateChecker =
                () -> {
                    boolean hasUpdate = updateService.hasUpdate();
                    if(hasUpdate)
                        player.sendMessage(Message.getMessage("&bA newer version of &esleep-most &bis available! &cPlease note that support is only given to the latest version"));
                };

        Thread thread = new Thread(updateChecker);
        thread.start();

    }
}
