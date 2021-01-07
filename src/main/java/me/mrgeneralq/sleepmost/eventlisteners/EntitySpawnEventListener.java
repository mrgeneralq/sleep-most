package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.repositories.FlagsRepository;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnEventListener implements Listener
{
	private final FlagsRepository flagsRepository = FlagsRepository.getInstance();

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e){

		World world = e.getEntity().getWorld();
		
		if(!isPhantom(e.getEntityType()))
			return;

		if(!this.flagsRepository.getPreventPhantomFlag().getController().getValueAt(world))
			return;

		e.setCancelled(true);
	}
	private boolean isPhantom(EntityType type) 
	{
		try {
			return type == EntityType.valueOf("PHANTOM");
		}
		catch(Exception ex) {
			return false;
		}
	}
}
