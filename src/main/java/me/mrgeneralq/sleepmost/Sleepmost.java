package me.mrgeneralq.sleepmost;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
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
		
		//init commands
		SleepmostCommand sleepmostCommand = new SleepmostCommand(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getUpdateService(), bootstrapper.getFlagService(), bootstrapper.getFlagsRepository());
		Bukkit.getPluginCommand("sleepmost").setExecutor(sleepmostCommand);
		getCommand("sleepmost").setTabCompleter(sleepmostCommand);
		
		//init listeners
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerSleepEventListener(this, bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(), bootstrapper.getFlagsRepository()), this);
		pm.registerEvents(new PlayerQuitEventListener(bootstrapper.getCooldownService()), this);
		pm.registerEvents(new SleepSkipEventListener(bootstrapper.getMessageService(), bootstrapper.getConfigService()), this);
		pm.registerEvents(new EntityTargetLivingEntityEventListener(bootstrapper.getFlagsRepository().getMobNoTargetFlag()), this);
		pm.registerEvents(new PlayerWorldChangeEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService()), this);
		pm.registerEvents(new PlayerJoinEventListener(this,bootstrapper.getUpdateService()), this);
		pm.registerEvents(new EntitySpawnEventListener(bootstrapper.getFlagsRepository().getPreventPhantomFlag()), this);
		pm.registerEvents(new TimeSkipEventListener(), this);
		
		Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
			notifyIfNewUpdateExists(bootstrapper.getUpdateService());
		});
	}
	private void notifyIfNewUpdateExists(IUpdateService updateService) 
	{
		if(updateService.hasUpdate())
			getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
	}
}
