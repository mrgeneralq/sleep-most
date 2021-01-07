package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.repositories.FlagsRepository;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetLivingEntityEventListener implements Listener{

	private final ISleepService sleepService;
	private final FlagsRepository flagsRepository = FlagsRepository.getInstance();

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

		if(!this.flagsRepository.getMobNoTargetFlag().getController().getValueAt(world))
			return;

		event.setCancelled(true);
	}

}
