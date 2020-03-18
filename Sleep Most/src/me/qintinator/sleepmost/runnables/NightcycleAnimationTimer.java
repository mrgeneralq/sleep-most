package me.qintinator.sleepmost.runnables;

import me.qintinator.sleepmost.enums.SleepSkipCause;
import me.qintinator.sleepmost.events.SleepSkipEvent;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISleepService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class NightcycleAnimationTimer extends BukkitRunnable {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final World world;


    public NightcycleAnimationTimer(ISleepService sleepService, IMessageService messageService, World world) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.world = world;
    }

    @Override
    public void run() {

        if(!sleepService.isNight(world)){
            world.setThundering(false);
            world.setStorm(false);
            Bukkit.getServer().getPluginManager().callEvent(new SleepSkipEvent(world, SleepSkipCause.NightTime));
            this.cancel();
        }

        world.setTime(world.getTime()+ 85);
    }
}
