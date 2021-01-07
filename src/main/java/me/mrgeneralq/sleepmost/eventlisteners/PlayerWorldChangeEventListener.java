package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerWorldChangeEventListener implements Listener {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public PlayerWorldChangeEventListener(ISleepService sleepService, IMessageService messageService){
        this.sleepService = sleepService;
        this.messageService = messageService;
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent e){
        Player player  = e.getPlayer();
        World world = player.getWorld();

        if(!player.hasPermission("sleepmost.notify"))
            return;

        if(sleepService.enabledForWorld(world))
            return;

        player.sendMessage(messageService.fromTemplate(MessageTemplate.CURRENTLY_DISABLED));
    }
}
