package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class SleepSkipEventListener implements Listener {

	private final ISleepService sleepService;
	private final IMessageService messageService;
	private final DataContainer dataContainer;

	public SleepSkipEventListener(ISleepService sleepService, IMessageService messageService) {
		this.sleepService = sleepService;
		this.messageService = messageService;
		this.dataContainer = DataContainer.getContainer();
	}

	@EventHandler
	public void onSleepSkip(SleepSkipEvent e){

		World world = e.getWorld();

		if(dataContainer.getRunningWorldsAnimation().contains(world))
			return;

		resetPhantomCounter(world);

		if(e.getCause() == SleepSkipCause.STORM){
			messageService.sendMessageToWorld(ConfigMessage.STORM_SKIPPED, world);
			return;
		}
		messageService.sendMessageToWorld(ConfigMessage.NIGHT_SKIPPED, world);
		return;

	}
	private void resetPhantomCounter(World world) 
	{
		try{

			for(Player p: world.getPlayers())
				p.setStatistic(Statistic.TIME_SINCE_REST, 0);
		}catch (IllegalArgumentException error){

		}
	}
}




