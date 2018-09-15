package me.qintinator.sleepmost;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onEnable() {
		
		
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(new OnSleep(this), this);

	}

}
