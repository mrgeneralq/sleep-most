package me.mrgeneralq.sleepmost.eventlisteners;

import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.statics.VersionController;


public class SleepSkipEventListener implements Listener {

	private final ISleepService sleepService;
	private final IMessageService messageService;
	private IConfigService configService;
	private final DataContainer dataContainer;

	public SleepSkipEventListener(ISleepService sleepService, IMessageService messageService, IConfigService configService) {
		this.sleepService = sleepService;
		this.messageService = messageService;
		this.configService = configService;
		this.dataContainer = DataContainer.getContainer();
	}

	@EventHandler
	public void onSleepSkip(SleepSkipEvent e){

		World world = e.getWorld();

		if(dataContainer.getRunningWorldsAnimation().contains(world))
			return;

		resetPhantomCounter(world);

		if(e.getCause() == SleepSkipCause.STORM){
			//messageService.sendMessageToWorld(ConfigMessage.STORM_SKIPPED, world);
			messageService.sendNightSkippedMessage(e.getWorld(), e.getLastSleeperName(), e.getCause());

			if(configService.getTitleStormSkippedEnabled() && !VersionController.isOldVersion()){
				for(Player p: world.getPlayers())
					p.sendTitle(configService.getTitleStormSkippedTitle().replaceAll("%player%", p.getName())
							, configService.getTitleStormSkippedSubTitle().replaceAll("%player%", p.getName())
							, 10,70,20);
			}

			if(configService.getSoundStormSkippedEnabled()){
				for(Player p: world.getPlayers())
					p.playSound(p.getLocation(), configService.getSoundStormSkippedSound(), 0.4F , 1F);
			}

			return;
		}

		if(configService.getTitleNightSkippedEnabled() && !VersionController.isOldVersion()) {
			for (Player p : world.getPlayers())

				p.sendTitle(configService.getTitleNightSkippedTitle().replaceAll("%player%", p.getName()),
						configService.getTitleNightSkippedSubTitle().replaceAll("%player%",p.getName()), 10, 70, 20);
			messageService.sendMessageToWorld(ConfigMessage.NIGHT_SKIPPED, world);
		}

		if(configService.getSoundNightSkippedEnabled()){
			for(Player p: world.getPlayers())
				p.playSound(p.getLocation(), configService.getSoundNightSkippedSound(), 0.4F , 1F);
		}
	}


	private void resetPhantomCounter(World world) 
	{
		/*
		* DISCLAIMER: Statistic and TIME_SINCE_REST Does not exist
		* in older versions of Minecraft
		 */
		try{
			for(Player p: world.getPlayers())
				p.setStatistic(Statistic.TIME_SINCE_REST, 0);
		}catch (NoSuchFieldError error){
			// statistic did not exist yet in some versions
		}
	}
}




