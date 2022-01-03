package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.enums.SleepState;
import me.mrgeneralq.sleepmost.events.PlayerSleepStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerSleepStateChangeEventListener implements Listener {

    @EventHandler
    public void onPlayerSleepStateChange(PlayerSleepStateChangeEvent e){

        SleepState sleepState = e.getSleepState();






    }

}
