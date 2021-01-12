package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.flags.MobNoTargetFlag;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetLivingEntityEventListener implements Listener{

	private final MobNoTargetFlag mobNoTargetFlag;

	public EntityTargetLivingEntityEventListener(MobNoTargetFlag mobNoTargetFlag) {
		this.mobNoTargetFlag = mobNoTargetFlag;
	}

	@EventHandler
	public void onMobTarget(EntityTargetLivingEntityEvent event) {
		if(!(event.getTarget() instanceof Player))
			return;

		Player player = (Player) event.getTarget();
		World world = player.getWorld();

		if(!player.isSleeping())
			return;

		if(!this.mobNoTargetFlag.getValueAt(world))
			return;

		event.setCancelled(true);
	}
}
