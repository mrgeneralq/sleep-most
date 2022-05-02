package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBedEnterEventListener implements Listener {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final ICooldownService cooldownService;
    private final DataContainer dataContainer;
    private final IFlagsRepository flagsRepository;
    private final IBossBarService bossBarService;
    private final IWorldPropertyService worldPropertyService;

    public PlayerBedEnterEventListener(ISleepService sleepService,
                                       IMessageService messageService,
                                       ICooldownService cooldownService,
                                       IFlagsRepository flagsRepository,
                                       IBossBarService bossBarService,
                                       IWorldPropertyService worldPropertyService
    ) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.cooldownService = cooldownService;
        this.flagsRepository = flagsRepository;
        this.bossBarService = bossBarService;
        this.worldPropertyService = worldPropertyService;
        this.dataContainer = DataContainer.getContainer();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBedEnter(PlayerBedEnterEvent e) {

        Player player = e.getPlayer();
        World world = player.getWorld();

        if(e.isCancelled())
            return;

        //check if sleeping during storms is allowed
        if (world.isThundering() && !this.flagsRepository.getStormSleepFlag().getValueAt(world)) {

            String preventSleepStormMessage = messageService.getMessagePrefixed(MessageKey.NO_SLEEP_THUNDERSTORM)
                    .setPlayer(player)
                    .setWorld(world)
                    .build();

            this.messageService.sendMessage(player, messageService.getMessagePrefixed(preventSleepStormMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());

            e.setCancelled(true);
            return;
        }

        //check if sleep is allowed in world
        if(this.flagsRepository.getPreventSleepFlag().getValueAt(world)) {
            String sleepPreventedConfigMessage = messageService.getMessagePrefixed(MessageKey.SLEEP_PREVENTED).build();
            this.messageService.sendMessage(player, messageService.getMessagePrefixed(sleepPreventedConfigMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());
            e.setCancelled(true);
            return;
        }


        if(this.worldPropertyService.getWorldProperties(world).isInsomniaEnabled()){
            String insomniaMessage = this.messageService.getMessagePrefixed(MessageKey.INSOMNIA_NOT_SLEEPY)
                    .setWorld(world)
                    .setPlayer(player)
                    .build();
            this.messageService.sendMessage(player,insomniaMessage);
            e.setCancelled(true);
            return;
        }

        if(!this.sleepService.isPlayerAsleep(player))
        this.sleepService.setSleeping(player , true);

    }
}
