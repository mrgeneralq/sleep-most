package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
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
