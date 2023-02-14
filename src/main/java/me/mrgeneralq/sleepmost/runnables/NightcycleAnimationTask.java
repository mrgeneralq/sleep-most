package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class NightcycleAnimationTask extends BukkitRunnable {

    private final ISleepService sleepService;
    private final DataContainer dataContainer = DataContainer.getContainer();
    private final ISleepMostWorldService sleepMostWorldService;
    private final IConfigService configService;
    private final IMessageService messageService;
    private final IFlagsRepository flagsRepository;
    private final World world;
    private final String lastSleeperName;
    private final String lastSLeeperDisplayName;
    private final SleepSkipCause skipCause;
    private final List<OfflinePlayer> peopleWhoSlept;
    private int iterationCount = 1;

    public NightcycleAnimationTask(ISleepService sleepService, IFlagsRepository flagsRepository, World world, Player lastSleeper, List<OfflinePlayer> peopleWhoSlept, SleepSkipCause sleepSkipCause, ISleepMostWorldService sleepMostWorldService, IMessageService messageService, IConfigService configService) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.world = world;
        this.lastSleeperName = lastSleeper.getName();
        this.lastSLeeperDisplayName = lastSleeper.getDisplayName();
        this.skipCause = sleepSkipCause;
        this.peopleWhoSlept = peopleWhoSlept;

        this.sleepMostWorldService = sleepMostWorldService;
        this.messageService = messageService;
        this.configService = configService;
    }

    @Override
    public void run() {

        if(!sleepService.isNight(world)){

            SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);
            sleepMostWorld.setTimeCycleAnimationIsRunning(false);

            this.sleepService.executeSleepReset(world, this.lastSleeperName, this.lastSLeeperDisplayName ,  this.peopleWhoSlept , this.skipCause);
            this.cancel();

            if(this.flagsRepository.getClockAnimationFlag().getValueAt(world) && ServerVersion.CURRENT_VERSION.supportsTitles() && skipCause == SleepSkipCause.NIGHT_TIME){

                List<Player> playerList = (flagsRepository.getNonSleepingClockAnimationFlag().getValueAt(world) ?
                        world.getPlayers():
                        peopleWhoSlept.stream().filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList()));

                for(Player p: playerList){

                    String clockTitle = this.messageService.getMessage(MessageKey.CLOCK_TITLE)
                            .setTime(0)
                            .build();

                    String clockSubTitle = this.messageService.getMessage(MessageKey.CLOCK_SUBTITLE)
                                    .build();


                    p.sendTitle( clockTitle ,clockSubTitle, 0,70,20);
                }
            }

        }else{

            /* **************************************************
             *  All code in this block only runs during the night
             ***************************************************/

            if(this.flagsRepository.getClockAnimationFlag().getValueAt(world) && ServerVersion.CURRENT_VERSION.supportsTitles()){

                List<Player> playerList = (flagsRepository.getNonSleepingClockAnimationFlag().getValueAt(world) ?
                        world.getPlayers():
                        peopleWhoSlept.stream().filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList()));

                for(Player p: playerList){

                    String clockTitle = this.messageService.getMessage(MessageKey.CLOCK_TITLE)
                            .setTime((int) world.getTime())
                            .build();

                    String clockSubTitle = this.messageService.getMessage(MessageKey.CLOCK_SUBTITLE)
                            .build();


                    p.sendTitle( clockTitle ,clockSubTitle, 0,70,20);
                }
            }
        }

        //85 by default
        int calculatedSpeed = configService.getNightcycleAnimationSpeed();


        if(this.flagsRepository.getDynamicAnimationSpeedFlag().getValueAt(world)){

            int sleepingPlayers = this.sleepService.getSleepersAmount(world);
            int totalPlayers = world.getPlayers().size();
            double sleepRation = (double) sleepingPlayers/ (double)totalPlayers;

            int maxSpeed = 150;
            int minSpeed = 30;

            calculatedSpeed = Math.min ((int) Math.round((sleepRation * (maxSpeed - minSpeed)) + minSpeed),maxSpeed);

        }
        world.setTime(world.getTime() + calculatedSpeed);
    }
}
