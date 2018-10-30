package me.qintinator.sleepmost;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.qintinator.sleepmost.eventlisteners.OnMobTarget;
import me.qintinator.sleepmost.eventlisteners.OnSleep;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onEnable() {
		
		
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(new OnSleep(this), this);
		Bukkit.getPluginManager().registerEvents(new OnMobTarget(this), this);

	}

}
