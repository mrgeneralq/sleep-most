package me.mrgeneralq.sleepmost.eventlisteners.hooks;

import dev.geco.gsit.api.event.PlayerPoseEvent;
import dev.geco.gsit.api.event.PlayerStopPoseEvent;
import dev.geco.gsit.model.Pose;
import dev.geco.gsit.model.PoseType;
import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GSitEventListener implements Listener {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final IFlagsRepository flagsRepository;
    private final ISleepMostWorldService sleepMostWorldService;
    private final IInsomniaService insomniaService;

    public GSitEventListener(ISleepService sleepService,
                                       IMessageService messageService,
                                       ICooldownService cooldownService,
                                       IFlagsRepository flagsRepository,
                                       IBossBarService bossBarService,
                                       ISleepMostWorldService sleepMostWorldService,
                                       IInsomniaService insomniaService
    ) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.flagsRepository = flagsRepository;
        this.sleepMostWorldService = sleepMostWorldService;
        this.insomniaService = insomniaService;
    }

    @EventHandler
    public void onPlayerPose(PlayerPoseEvent e){

        Player player = e.getPlayer();
        World world = player.getWorld();
        Pose pose = e.getPose();

        if(!this.flagsRepository.getGSitHookFlag().getValueAt(world))
            return;

        if(!this.sleepService.isEnabledAt(world))
            return;

        if(!this.sleepService.isSleepingPossible(world))
            return;

        if(!this.flagsRepository.getGSitSleepFlag().getValueAt(world))
            return;

        if(pose.getPoseType() != PoseType.LAY && pose.getPoseType() != PoseType.LAY_BACK)
            return;

        if(!this.sleepService.isSleepingPossible(world))
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

        SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);
        if(sleepMostWorld.isFrozen()){

            String longerNightsSleepPreventedMsg = this.messageService.getMessagePrefixed(MessageKey.SLEEP_PREVENTED_LONGER_NIGHT)
                    .setWorld(world)
                    .setPlayer(player)
                    .build();
            this.messageService.sendMessage(player, longerNightsSleepPreventedMsg);
            return;
        }

        if(!this.sleepService.isPlayerAsleep(player))
            this.sleepService.setSleeping(player , true);
    }

    @EventHandler
    public void onGetUpPose(PlayerStopPoseEvent e){

        Player player = e.getPlayer();
        this.sleepService.setSleeping(player, false);
    }

}
