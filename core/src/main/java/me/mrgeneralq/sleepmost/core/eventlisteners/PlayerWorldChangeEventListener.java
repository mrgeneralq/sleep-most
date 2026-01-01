package me.mrgeneralq.sleepmost.core.eventlisteners;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.interfaces.IBossBarService;
import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.core.statics.DataContainer;
import me.mrgeneralq.sleepmost.core.statics.ServerVersion;
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
