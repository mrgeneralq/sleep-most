package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.repositories.SleepFlagRepository;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetLivingEntityEventListener implements Listener{

	private final ISleepService sleepService;

	public EntityTargetLivingEntityEventListener(ISleepService sleepService) {
		this.sleepService = sleepService;
	}

	@EventHandler
	public void onMobTarget(EntityTargetLivingEntityEvent event) {
		if(!(event.getTarget() instanceof Player))
			return;

		Player player = (Player) event.getTarget();
		World world = player.getWorld();

		if(!player.isSleeping())
			return;

		if(!SleepFlagRepository.getInstance().getMobNoTargetFlag().getController().getValueAt(world))
			return;

		event.setCancelled(true);
	}

}
