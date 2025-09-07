package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.models.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.IBossBarService;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.repositories.containers.DataContainer;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerWorldChangeEventListener implements Listener {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final IBossBarService bossBarService;
    private final DataContainer dataContainer = DataContainer.getContainer();

    public PlayerWorldChangeEventListener(ISleepService sleepService, IMessageService messageService, IBossBarService bossBarService){
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.bossBarService = bossBarService;
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
        
        Player player  = e.getPlayer();
        World world = player.getWorld();

        this.dataContainer.setPlayerSleeping(e.getPlayer(), false);

        if(ServerVersion.CURRENT_VERSION.supportsBossBars()){
            this.bossBarService.unregisterPlayer(e.getFrom(), player);
            this.bossBarService.registerPlayer(world,player);
        }

        if(!player.hasPermission("sleepmost.notify"))
            return;

        if(sleepService.isEnabledAt(world))
            return;

        this.messageService.sendMessage(player, messageService.getMessagePrefixed(MessageKey.CURRENTLY_DISABLED).build());
    }
}
