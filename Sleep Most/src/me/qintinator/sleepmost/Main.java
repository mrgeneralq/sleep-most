package me.qintinator.sleepmost;
import me.qintinator.sleepmost.bstats.Metrics;
import me.qintinator.sleepmost.commands.SleepmostCommand;
import me.qintinator.sleepmost.commands.subcommands.SetFlagCommand;
import me.qintinator.sleepmost.eventlisteners.*;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.statics.Bootstrapper;
import me.qintinator.sleepmost.statics.VersionController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onEnable() {

		if(VersionController.isOldVersion())
			Bukkit.getLogger().warning("You are using an older Minecraft version that requires a different way of calculating who is asleep.");


		Metrics metric = new Metrics(this);

		saveDefaultConfig();
		Bootstrapper bootstrapper = Bootstrapper.getBootstrapper();
		bootstrapper.initialize(this);
		IMessageService messageService = bootstrapper.getMessageService();

		Bukkit.getPluginCommand("sleepmost").setExecutor(new SleepmostCommand(bootstrapper.getSleepService(),messageService, bootstrapper.getSleepFlagService(), bootstrapper.getUpdateService()));


		Bukkit.getPluginManager().registerEvents(new OnSleep(this,bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(),bootstrapper.getSleepFlagService()), this);
		Bukkit.getPluginManager().registerEvents(new OnLeave(bootstrapper.getCooldownService()), this);
		Bukkit.getPluginManager().registerEvents(new OnSleepSkip(bootstrapper.getSleepService(), bootstrapper.getMessageService()), this);
		Bukkit.getPluginManager().registerEvents(new OnMobTarget(bootstrapper.getSleepService(), bootstrapper.getSleepFlagService()), this);
		Bukkit.getPluginManager().registerEvents(new OnPlayerWorldChange(bootstrapper.getSleepService()), this);
		Bukkit.getPluginManager().registerEvents(new OnPlayerJoin(bootstrapper.getUpdateService()), this);
		Bukkit.getPluginManager().registerEvents(new OnEntitySpawn(bootstrapper.getSleepFlagService()), this);


		getCommand("sleepmost").setTabCompleter(new SleepmostCommand(bootstrapper.getSleepService(),messageService, bootstrapper.getSleepFlagService(), bootstrapper.getUpdateService()));

		Runnable updateChecker =
				() -> {
					boolean hasUpdate = bootstrapper.getUpdateService().hasUpdate();
					if(hasUpdate)
						Bukkit.getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
				};

		Thread thread = new Thread(updateChecker);
		thread.start();


	}
}
