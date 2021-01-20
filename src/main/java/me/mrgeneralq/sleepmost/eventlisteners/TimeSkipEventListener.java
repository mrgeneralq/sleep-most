package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.event.world.TimeSkipEvent.SkipReason;

public class TimeSkipEventListener implements Listener
{
    private final ISleepService sleepService;

    public TimeSkipEventListener(ISleepService sleepService) {
        this.sleepService = sleepService;
    }

    @EventHandler
    public void onTimeSkip(TimeSkipEvent event)
    {
        if(!this.sleepService.isEnabledAt(event.getWorld()))
            return;

        if(event.getSkipReason() == SkipReason.NIGHT_SKIP) 
        {
        	event.setCancelled(true);
        }
    }
}
