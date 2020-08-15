package me.qintinator.sleepmost.runnables;

import me.qintinator.sleepmost.enums.SleepSkipCause;
import me.qintinator.sleepmost.events.SleepSkipEvent;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.statics.DataContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class NightcycleAnimationTimer extends BukkitRunnable {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final World world;
    private final DataContainer dataContainer;


    public NightcycleAnimationTimer(ISleepService sleepService, IMessageService messageService, World world) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.world = world;
        this.dataContainer = DataContainer.getContainer();
    }

    @Override
    public void run() {

        if(!sleepService.isNight(world)){

            //remove animation checker
            this.dataContainer.getRunningWorldsAnimation().remove(world);

            world.setThundering(false);
            world.setStorm(false);

            Bukkit.getServer().getPluginManager().callEvent(new SleepSkipEvent(world, SleepSkipCause.NIGHT_TIME));
            this.cancel();
        }

        world.setTime(world.getTime() + 85);
    }
}
