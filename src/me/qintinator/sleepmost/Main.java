package me.qintinator.sleepmost;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.qintinator.sleepmost.bstats.Metrics;
import me.qintinator.sleepmost.commands.SleepmostCommand;
import me.qintinator.sleepmost.eventlisteners.OnEntitySpawn;
import me.qintinator.sleepmost.eventlisteners.OnLeave;
import me.qintinator.sleepmost.eventlisteners.OnMobTarget;
import me.qintinator.sleepmost.eventlisteners.OnPlayerJoin;
import me.qintinator.sleepmost.eventlisteners.OnPlayerWorldChange;
import me.qintinator.sleepmost.eventlisteners.OnSleep;
import me.qintinator.sleepmost.eventlisteners.OnSleepSkip;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.IUpdateService;
import me.qintinator.sleepmost.statics.Bootstrapper;
import me.qintinator.sleepmost.statics.VersionController;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onEnable() {

		if(VersionController.isOldVersion())
			Bukkit.getLogger().warning("You are using an older Minecraft version that requires a different way of calculating who is asleep.");
		
		//general
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
		Bukkit.getPluginManager().registerEvents(new OnSleep(this, bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(),bootstrapper.getSleepFlagService()), this);
		Bukkit.getPluginManager().registerEvents(new OnLeave(bootstrapper.getCooldownService()), this);
		Bukkit.getPluginManager().registerEvents(new OnSleepSkip(bootstrapper.getSleepService(), bootstrapper.getMessageService()), this);
		Bukkit.getPluginManager().registerEvents(new OnMobTarget(bootstrapper.getSleepService(), bootstrapper.getSleepFlagService()), this);
		Bukkit.getPluginManager().registerEvents(new OnPlayerWorldChange(bootstrapper.getSleepService()), this);
		Bukkit.getPluginManager().registerEvents(new OnPlayerJoin(bootstrapper.getUpdateService()), this);
		Bukkit.getPluginManager().registerEvents(new OnEntitySpawn(bootstrapper.getSleepFlagService()), this);
		
		//log if this version of the plugin is not the latest
		new Thread(() -> notifyIfNewUpdateExists(updateService)).start();
	}
	private void notifyIfNewUpdateExists(IUpdateService updateService) 
	{
		if(updateService.hasUpdate())
			Bukkit.getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
	}
}
