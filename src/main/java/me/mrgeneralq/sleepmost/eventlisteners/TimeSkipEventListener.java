package me.mrgeneralq.sleepmost.eventlisteners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.event.world.TimeSkipEvent.SkipReason;

public class TimeSkipEventListener implements Listener
{
    @EventHandler
    public void onTimeSkip(TimeSkipEvent event)
    {
        if(event.getSkipReason() == SkipReason.NIGHT_SKIP) 
        {
        	event.setCancelled(true);
        }
    }
}
