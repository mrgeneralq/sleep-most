package me.mrgeneralq.sleepmost.core.eventlisteners;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.core.interfaces.IInsomniaService;
import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.interfaces.ISleepService;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerConsumeEventListener implements Listener {


    private final ISleepService sleepService;
    private final IInsomniaService insomniaService;
    private final IMessageService messageService;
    private final IFlagsRepository flagsRepository;

    public PlayerConsumeEventListener(ISleepService sleepService, IInsomniaService insomniaService, IMessageService messageService, IFlagsRepository flagsRepository) {
        this.sleepService = sleepService;
        this.insomniaService = insomniaService;
        this.messageService = messageService;
        this.flagsRepository = flagsRepository;
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent e){

        Player player = e.getPlayer();
        World world = player.getWorld();

        if(!this.sleepService.isEnabledAt(world))
            return;

        if(!this.flagsRepository.getInsomniaMilkFlag().getValueAt(world))
            return;

        if(e.getItem().getType() != Material.MILK_BUCKET)
            return;

        this.insomniaService.disableInsomnia(player, world);
        this.messageService.sendMessage(player, this.messageService.getMessagePrefixed(MessageKey.INSOMNIA_FEELING_SLEEPY).build());

    }

}
