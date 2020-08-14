package me.qintinator.sleepmost;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.qintinator.sleepmost.bstats.Metrics;
import me.qintinator.sleepmost.commands.SleepmostCommand;
import me.qintinator.sleepmost.eventlisteners.EntitySpawnEventListener;
import me.qintinator.sleepmost.eventlisteners.PlayerQuitEventListener;
import me.qintinator.sleepmost.eventlisteners.EntityTargetLivingEntityEventListener;
import me.qintinator.sleepmost.eventlisteners.PlayerJoinEventListener;
import me.qintinator.sleepmost.eventlisteners.PlayerWorldChangeEventListener;
import me.qintinator.sleepmost.eventlisteners.PlayerSleepEventListener;
import me.qintinator.sleepmost.eventlisteners.SleepSkipEventListener;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.IUpdateService;
import me.qintinator.sleepmost.statics.Bootstrapper;
import me.qintinator.sleepmost.statics.VersionController;

public class Sleepmost extends JavaPlugin{
	
	
	@Override
	public void onEnable() {

		if(VersionController.isOldVersion())
			Bukkit.getLogger().warning("You are using an older Minecraft version that requires a different way of calculating who is asleep.");
		
		new Metrics(this);
		saveDefaultConfig();
		
		//init bootstrapper
		Bootstrapper bootstrapper = Bootstrapper.getBootstrapper();
		bootstrapper.initialize(this);
		
		IMessageService messageService = bootstrapper.getMessageService();
		IUpdateService updateService = bootstrapper.getUpdateService();
		
		//init commands
		Bukkit.getPluginCommand("sleepmost").setExecutor(new SleepmostCommand(bootstrapper.getSleepService(), messageService, bootstrapper.getSleepFlagService(), bootstrapper.getUpdateService()));
		getCommand("sleepmost").setTabCompleter(new SleepmostCommand(bootstrapper.getSleepService(), messageService, bootstrapper.getSleepFlagService(), bootstrapper.getUpdateService()));
		
		//init listeners
		Bukkit.getPluginManager().registerEvents(new PlayerSleepEventListener(this, bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(),bootstrapper.getSleepFlagService()), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitEventListener(bootstrapper.getCooldownService()), this);
		Bukkit.getPluginManager().registerEvents(new SleepSkipEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService()), this);
		Bukkit.getPluginManager().registerEvents(new EntityTargetLivingEntityEventListener(bootstrapper.getSleepService(), bootstrapper.getSleepFlagService()), this);
		Bukkit.getPluginManager().registerEvents(new PlayerWorldChangeEventListener(bootstrapper.getSleepService()), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(this,bootstrapper.getUpdateService()), this);
		Bukkit.getPluginManager().registerEvents(new EntitySpawnEventListener(bootstrapper.getSleepFlagService()), this);


		Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
			notifyIfNewUpdateExists(updateService);
		});

	}
	private void notifyIfNewUpdateExists(IUpdateService updateService) 
	{
		if(updateService.hasUpdate())
			Bukkit.getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
	}
}
