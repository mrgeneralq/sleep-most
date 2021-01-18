package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NightcycleAnimationTask extends BukkitRunnable {

    private final ISleepService sleepService;
    private final DataContainer dataContainer = DataContainer.getContainer();
    private final World world;
    private final String lastSleeperName;
    private final String lastSLeeperDisplayName;
    private final SleepSkipCause skipCause;

    public NightcycleAnimationTask(ISleepService sleepService, World world, Player lastSleeper, SleepSkipCause sleepSkipCause) {
        this.sleepService = sleepService;
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
        }

        world.setTime(world.getTime() + 85);
    }
}
