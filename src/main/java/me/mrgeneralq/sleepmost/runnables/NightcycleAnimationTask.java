package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import me.mrgeneralq.sleepmost.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NightcycleAnimationTask extends BukkitRunnable {

    private final ISleepService sleepService;
    private final DataContainer dataContainer = DataContainer.getContainer();
    private final IFlagsRepository flagsRepository;
    private final World world;
    private final String lastSleeperName;
    private final String lastSLeeperDisplayName;
    private final SleepSkipCause skipCause;

    public NightcycleAnimationTask(ISleepService sleepService, IFlagsRepository flagsRepository, World world, Player lastSleeper, SleepSkipCause sleepSkipCause) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.world = world;
        this.lastSleeperName = lastSleeper.getName();
        this.lastSLeeperDisplayName = lastSleeper.getDisplayName();
        this.skipCause = sleepSkipCause;
    }

    @Override
    public void run() {

        if(!sleepService.isNight(world)){

            //remove animation checker
            this.dataContainer.setAnimationRunning(world, false);

            this.sleepService.executeSleepReset(world, this.lastSleeperName, this.lastSLeeperDisplayName, this.skipCause);
            this.cancel();

            if(this.flagsRepository.getClockAnimationFlag().getValueAt(world) && ServerVersion.CURRENT_VERSION.supportsTitles()){

                for(Player p: world.getPlayers()){
                    String timeString = TimeUtils.getTimeStringByTicks(0);
                    p.sendTitle(ChatColor.AQUA + timeString ,ChatColor.GREEN + "fast forward >>>", 0,70,20);
                }
            }

        }else{
            if(this.flagsRepository.getClockAnimationFlag().getValueAt(world) && ServerVersion.CURRENT_VERSION.supportsTitles()){

                for(Player p: world.getPlayers()){
                    String timeString = TimeUtils.getTimeStringByTicks(world.getTime());
                    p.sendTitle(ChatColor.AQUA + timeString ,ChatColor.GREEN + "fast forward >>>", 0,70,20);
                }
            }
        }

        world.setTime(world.getTime() + 85);
    }
}
