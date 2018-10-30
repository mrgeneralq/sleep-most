package me.qintinator.sleepmost.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import me.qintinator.sleepmost.Main;

public class OnMobTarget implements Listener{
	
	private final Main main;
	
	public OnMobTarget(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onMobTarget(EntityTargetEvent event) {
		
		if(!(event.getTarget() instanceof Player))
			return;
		
		Player player = (Player) event.getTarget();
		
		if(player.isSleeping() && main.getConfig().getBoolean("sleep.mob-no-target"))
		event.setCancelled(true);
	}

}
