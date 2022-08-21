package me.mrgeneralq.sleepmost.eventlisteners.hooks;

import dev.geco.gsit.api.event.PlayerPoseEvent;
import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GSitEventListener implements Listener {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final ICooldownService cooldownService;
    private final DataContainer dataContainer;
    private final IFlagsRepository flagsRepository;
    private final IBossBarService bossBarService;
    private final IWorldPropertyService worldPropertyService;
    private final IInsomniaService insomniaService;

    public GSitEventListener(ISleepService sleepService,
                                       IMessageService messageService,
                                       ICooldownService cooldownService,
                                       IFlagsRepository flagsRepository,
                                       IBossBarService bossBarService,
                                       IWorldPropertyService worldPropertyService,
                                       IInsomniaService insomniaService
    ) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.cooldownService = cooldownService;
        this.flagsRepository = flagsRepository;
        this.bossBarService = bossBarService;
        this.worldPropertyService = worldPropertyService;
        this.insomniaService = insomniaService;
        this.dataContainer = DataContainer.getContainer();
    }

    @EventHandler
    public void onPlayerPose(PlayerPoseEvent e){

        Player player = e.getPlayer();
        World world = player.getWorld();
        Pose pose = e.getPoseSeat().getPose();

        if(pose != Pose.SLEEPING)
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
            return;
        }

        //check if sleep is allowed in world
        if(this.flagsRepository.getPreventSleepFlag().getValueAt(world)) {

            String sleepPreventedConfigMessage = messageService.getMessagePrefixed(MessageKey.SLEEP_PREVENTED).build();
            this.messageService.sendMessage(player, messageService.getMessagePrefixed(sleepPreventedConfigMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());
            return;
        }

        if(this.insomniaService.hasInsomniaEnabled(player)){

            String insomniaMessage = this.messageService.getMessagePrefixed(MessageKey.INSOMNIA_NOT_SLEEPY)
                    .setWorld(world)
                    .setPlayer(player)
                    .build();
            this.messageService.sendMessage(player,insomniaMessage);
            return;
        }

        if(!this.sleepService.isPlayerAsleep(player))
            this.sleepService.setSleeping(player , true);

    }
}
