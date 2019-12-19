package me.qintinator.sleepmost;

import me.qintinator.sleepmost.eventlisteners.OnLeave;
import me.qintinator.sleepmost.eventlisteners.OnSleepSkip;
import me.qintinator.sleepmost.statics.Bootstrapper;
import me.qintinator.sleepmost.statics.ConfigMessageMapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.qintinator.sleepmost.eventlisteners.OnMobTarget;
import me.qintinator.sleepmost.eventlisteners.OnSleep;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onEnable() {


		saveDefaultConfig();
		Bootstrapper bootstrapper = Bootstrapper.getBootstrapper();
		bootstrapper.initialize(this);

		Bukkit.getPluginManager().registerEvents(new OnSleep(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService()), this);
		Bukkit.getPluginManager().registerEvents(new OnLeave(bootstrapper.getCooldownService()), this);
		Bukkit.getPluginManager().registerEvents(new OnSleepSkip(bootstrapper.getSleepService(), bootstrapper.getMessageService()), this);
		Bukkit.getPluginManager().registerEvents(new OnMobTarget(bootstrapper.getSleepService()), this);

	}

}
