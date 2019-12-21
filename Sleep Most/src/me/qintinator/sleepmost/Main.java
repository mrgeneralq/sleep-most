package me.qintinator.sleepmost;

import me.qintinator.sleepmost.commands.SleepmostCommand;
import me.qintinator.sleepmost.eventlisteners.*;
import me.qintinator.sleepmost.statics.Bootstrapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onEnable() {


		saveDefaultConfig();
		Bootstrapper bootstrapper = Bootstrapper.getBootstrapper();
		bootstrapper.initialize(this);

		Bukkit.getPluginCommand("sleepmost").setExecutor(new SleepmostCommand(bootstrapper.getSleepService()));

		Bukkit.getPluginManager().registerEvents(new OnSleep(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService()), this);
		Bukkit.getPluginManager().registerEvents(new OnLeave(bootstrapper.getCooldownService()), this);
		Bukkit.getPluginManager().registerEvents(new OnSleepSkip(bootstrapper.getSleepService(), bootstrapper.getMessageService()), this);
		Bukkit.getPluginManager().registerEvents(new OnMobTarget(bootstrapper.getSleepService()), this);
		Bukkit.getPluginManager().registerEvents(new OnPlayerWorldChange(bootstrapper.getSleepService()), this);

	}


}
