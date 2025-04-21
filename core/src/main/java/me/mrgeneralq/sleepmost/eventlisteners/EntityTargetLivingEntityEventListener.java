package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetLivingEntityEventListener implements Listener{

	private final IFlagsRepository flagsRepository;

	public EntityTargetLivingEntityEventListener(IFlagsRepository flagsRepository) {
		this.flagsRepository = flagsRepository;
	}

	@EventHandler
	public void onMobTarget(EntityTargetLivingEntityEvent event) {
		if(!(event.getTarget() instanceof Player))
			return;

		Player player = (Player) event.getTarget();
		World world = player.getWorld();

		if(!player.isSleeping())
			return;

		if(!this.flagsRepository.getMobNoTargetFlag().getValueAt(world))
			return;

		event.setCancelled(true);
	}
}
