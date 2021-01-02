package me.mrgeneralq.sleepmost;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import me.mrgeneralq.sleepmost.mappers.SleepFlagMapper;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrgeneralq.sleepmost.commands.SleepmostCommand;
import me.mrgeneralq.sleepmost.eventlisteners.EntitySpawnEventListener;
import me.mrgeneralq.sleepmost.eventlisteners.EntityTargetLivingEntityEventListener;
import me.mrgeneralq.sleepmost.eventlisteners.PlayerJoinEventListener;
import me.mrgeneralq.sleepmost.eventlisteners.PlayerQuitEventListener;
import me.mrgeneralq.sleepmost.eventlisteners.PlayerSleepEventListener;
import me.mrgeneralq.sleepmost.eventlisteners.PlayerWorldChangeEventListener;
import me.mrgeneralq.sleepmost.eventlisteners.SleepSkipEventListener;
import me.mrgeneralq.sleepmost.eventlisteners.TimeSkipEventListener;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import me.mrgeneralq.sleepmost.statics.Bootstrapper;

public class Sleepmost extends JavaPlugin{
	
	
	@Override
	public void onEnable() {

		if(ServerVersion.CURRENT_VERSION.sleepCalculatedDifferently())
			getLogger().warning("You are using an older Minecraft version that requires a different way of calculating who is asleep.");
		
		saveDefaultConfig();
		
		//init metrics
		final int bStatsID = 6212;
		new Metrics(this, bStatsID);
		
		//init bootstrapper
		Bootstrapper bootstrapper = Bootstrapper.getBootstrapper();
		bootstrapper.initialize(this);

		SleepFlagMapper sleepFlagMapper = SleepFlagMapper.getMapper();
		sleepFlagMapper.setup(bootstrapper.getSleepFlagService());

		
		IMessageService messageService = bootstrapper.getMessageService();
		IUpdateService updateService = bootstrapper.getUpdateService();
		
		//init commands
		Bukkit.getPluginCommand("sleepmost").setExecutor(new SleepmostCommand(bootstrapper.getSleepService(), messageService, bootstrapper.getSleepFlagService(), bootstrapper.getUpdateService()));
		getCommand("sleepmost").setTabCompleter(new SleepmostCommand(bootstrapper.getSleepService(), messageService, bootstrapper.getSleepFlagService(), bootstrapper.getUpdateService()));
		
		//init listeners
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerSleepEventListener(this, bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(),bootstrapper.getSleepFlagService()), this);
		pm.registerEvents(new PlayerQuitEventListener(bootstrapper.getCooldownService()), this);
		pm.registerEvents(new SleepSkipEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getConfigService()), this);
		pm.registerEvents(new EntityTargetLivingEntityEventListener(bootstrapper.getSleepService(), bootstrapper.getSleepFlagService()), this);
		pm.registerEvents(new PlayerWorldChangeEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService()), this);
		pm.registerEvents(new PlayerJoinEventListener(this,bootstrapper.getUpdateService()), this);
		pm.registerEvents(new EntitySpawnEventListener(bootstrapper.getSleepFlagService()), this);
		pm.registerEvents(new TimeSkipEventListener(), this);
		
		Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
			notifyIfNewUpdateExists(updateService);
		});
	}
	private void notifyIfNewUpdateExists(IUpdateService updateService) 
	{
		if(updateService.hasUpdate())
			getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
	}
}
