package me.qintinator.sleepmost.eventlisteners;

import me.qintinator.sleepmost.enums.ConfigMessage;
import me.qintinator.sleepmost.enums.SleepSkipCause;
import me.qintinator.sleepmost.events.SleepSkipEvent;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISleepService;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class OnSleepSkip implements Listener {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public OnSleepSkip(ISleepService sleepService, IMessageService messageService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
    }

    @EventHandler
     public void OnSleepSkip(SleepSkipEvent e){

        World world = e.getWorld();
        SleepSkipCause cause = e.getCause();


        if(cause == SleepSkipCause.Storm){
            messageService.sendMessageToWorld(ConfigMessage.STORM_SKIPPED, world);
            return;
        }
            messageService.sendMessageToWorld(ConfigMessage.NIGHT_SKIPPED, world);
            return;
        }

     }




