package me.mrgeneralq.sleepmost;

import me.mrgeneralq.sleepmost.commands.SleepCommand;
import me.mrgeneralq.sleepmost.eventlisteners.*;
import me.mrgeneralq.sleepmost.services.UpdateService;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrgeneralq.sleepmost.commands.SleepmostCommand;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import me.mrgeneralq.sleepmost.statics.Bootstrapper;

public class Sleepmost extends JavaPlugin{


	@Override
	public void onEnable() {

		saveDefaultConfig();
		
		//init metrics
		final int bStatsID = 6212;
		new Metrics(this, bStatsID);
		
		//init bootstrapper
		Bootstrapper bootstrapper = Bootstrapper.getBootstrapper();
		bootstrapper.initialize(this);
		
		//init commands
		SleepmostCommand sleepmostCommand = new SleepmostCommand(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getUpdateService(), bootstrapper.getFlagService(), bootstrapper.getFlagsRepository(), bootstrapper.getConfigRepository());
		getCommand("sleepmost").setExecutor(sleepmostCommand);

		getCommand("sleep").setExecutor(new SleepCommand(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(), bootstrapper.getFlagsRepository()));
		
		//init listeners
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerSleepEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(), bootstrapper.getFlagsRepository()), this);
		pm.registerEvents(new PlayerQuitEventListener(bootstrapper.getCooldownService(), bootstrapper.getSleepService()), this);
		pm.registerEvents(new SleepSkipEventListener(bootstrapper.getMessageService(), bootstrapper.getConfigService(), bootstrapper.getSleepService(), bootstrapper.getFlagsRepository()), this);
		pm.registerEvents(new EntityTargetLivingEntityEventListener(bootstrapper.getFlagsRepository()), this);
		pm.registerEvents(new PlayerWorldChangeEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService()), this);
		pm.registerEvents(new PlayerJoinEventListener(this,  bootstrapper.getUpdateService(), bootstrapper.getMessageService()), this);
		pm.registerEvents(new EntitySpawnEventListener(bootstrapper.getFlagsRepository()), this);
		pm.registerEvents(new TimeSkipEventListener(bootstrapper.getSleepService()), this);
		pm.registerEvents(new WorldChangeEventListener(bootstrapper.getSleepService()), this);
		
		Bukkit.getScheduler().runTaskAsynchronously(this, () -> notifyIfNewUpdateExists(bootstrapper.getUpdateService()));
	}

	private void notifyIfNewUpdateExists(IUpdateService updateService) 
	{
		if(updateService.hasUpdate())
			getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
	}


}
