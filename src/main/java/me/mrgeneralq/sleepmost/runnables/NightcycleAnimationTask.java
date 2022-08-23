package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import me.mrgeneralq.sleepmost.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class NightcycleAnimationTask extends BukkitRunnable {

    private final ISleepService sleepService;
    private final DataContainer dataContainer = DataContainer.getContainer();
    private final IFlagsRepository flagsRepository;
    private final World world;
    private final String lastSleeperName;
    private final String lastSLeeperDisplayName;
    private final SleepSkipCause skipCause;
    private final List<OfflinePlayer> peopleWhoSlept;
    private int iterationCount = 1;

    public NightcycleAnimationTask(ISleepService sleepService, IFlagsRepository flagsRepository, World world, Player lastSleeper, List<OfflinePlayer> peopleWhoSlept, SleepSkipCause sleepSkipCause) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.world = world;
        this.lastSleeperName = lastSleeper.getName();
        this.lastSLeeperDisplayName = lastSleeper.getDisplayName();
        this.skipCause = sleepSkipCause;
        this.peopleWhoSlept = peopleWhoSlept;

    }

    @Override
    public void run() {

        if(!sleepService.isNight(world)){

            //remove animation checker
            this.dataContainer.setAnimationRunning(world, false);

            this.sleepService.executeSleepReset(world, this.lastSleeperName, this.lastSLeeperDisplayName ,  this.peopleWhoSlept , this.skipCause);
            this.cancel();

            if(this.flagsRepository.getClockAnimationFlag().getValueAt(world) && ServerVersion.CURRENT_VERSION.supportsTitles()){

                List<Player> playerList = (flagsRepository.getNonSleepingClockAnimationFlag().getValueAt(world) ?
                        world.getPlayers():
                        peopleWhoSlept.stream().filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList()));

                for(Player p: playerList){
                    String timeString = TimeUtils.getTimeStringByTicks(0);
                    p.sendTitle(ChatColor.AQUA + timeString ,ChatColor.GREEN + ">>>", 0,70,20);
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
                    String timeString = TimeUtils.getTimeStringByTicks(world.getTime());
                    p.sendTitle(ChatColor.AQUA + timeString ,ChatColor.GREEN + ">>>", 0,70,20);
                }
            }
        }

        int calculatedSpeed = 85;

        if(this.flagsRepository.getDynamicAnimationSpeed().getValueAt(world)){

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
