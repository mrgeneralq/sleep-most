package me.qintinator.sleepmost.eventlisteners;

import me.qintinator.sleepmost.interfaces.ISleepService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import me.qintinator.sleepmost.Main;

public class OnMobTarget implements Listener{
	
	private final ISleepService sleepService;
	
	public OnMobTarget(ISleepService sleepService) {
		this.sleepService = sleepService;
	}
	
	@EventHandler
	public void onMobTarget(EntityTargetLivingEntityEvent event) {

		//check if target is a player
		if(!(event.getTarget() instanceof Player))
			return;

		//cast player
		Player player = (Player) event.getTarget();

		//check if player is asleep
		if(!player.isSleeping())
			return;

		//check if mob targeting is enabled for world
		if(!sleepService.getMobNoTarget(player.getWorld()))
			return;

		// cancel te event
		event.setCancelled(true);
	}

}
