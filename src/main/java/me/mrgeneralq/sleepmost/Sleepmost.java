package me.mrgeneralq.sleepmost;

import dev.geco.gsit.api.GSitAPI;
import me.mrgeneralq.sleepmost.eventlisteners.*;
import me.mrgeneralq.sleepmost.eventlisteners.hooks.GSitEventListener;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.managers.HookManager;
import me.mrgeneralq.sleepmost.mappers.MessageMapper;
import me.mrgeneralq.sleepmost.runnables.Heartbeat;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrgeneralq.sleepmost.commands.SleepmostCommand;
import me.mrgeneralq.sleepmost.statics.Bootstrapper;

public class Sleepmost extends JavaPlugin {

	private static Sleepmost instance;
	private Bootstrapper bootstrapper;

	private IMessageService messageService;

	@Override
	public void onEnable() {

		instance = this;
		saveDefaultConfig();

		//init metrics
		final int bStatsID = 6212;
		new Metrics(this, bStatsID);

		//load the messages at start
		MessageMapper.getMapper().loadMessages();
		
		//init bootstrapper
		this.bootstrapper = Bootstrapper.getBootstrapper();
		bootstrapper.initialize(this);

		this.messageService = bootstrapper.getMessageService();

		//creating missing messages
		this.messageService.createMissingMessages();

		//check if boss bars are supported before registered
		if(ServerVersion.CURRENT_VERSION.supportsBossBars())
			this.registerBossBars();

		//init commands
		SleepmostCommand sleepmostCommand = new SleepmostCommand(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getUpdateService(), bootstrapper.getFlagService(), bootstrapper.getFlagsRepository(), bootstrapper.getConfigRepository(), bootstrapper.getCooldownService(), bootstrapper.getBossBarService(), bootstrapper.getSleepMostWorldService(), bootstrapper.getSleepMostPlayerService(), bootstrapper.getInsomniaService(), bootstrapper.getDebugService());
		getCommand("sleepmost").setExecutor(sleepmostCommand);

		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new PlayerBedEnterEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(), bootstrapper.getFlagsRepository(), bootstrapper.getBossBarService(), bootstrapper.getSleepMostWorldService(), bootstrapper.getInsomniaService()), this);
		pm.registerEvents(new PlayerQuitEventListener(bootstrapper.getCooldownService(), bootstrapper.getSleepService(), bootstrapper.getBossBarService(), bootstrapper.getSleepMostPlayerService()), this);
		pm.registerEvents(new EntityTargetLivingEntityEventListener(bootstrapper.getFlagsRepository()), this);
		pm.registerEvents(new PlayerWorldChangeEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getBossBarService()), this);
		pm.registerEvents(new PlayerJoinEventListener(this, bootstrapper.getUpdateService(), bootstrapper.getMessageService(), bootstrapper.getBossBarService(), bootstrapper.getSleepMostPlayerService()), this);
		pm.registerEvents(new EntitySpawnEventListener(bootstrapper.getFlagsRepository()), this);

		if(ServerVersion.CURRENT_VERSION.hasTimeSkipEvent()){
			pm.registerEvents(new TimeSkipEventListener(bootstrapper.getSleepService()), this);
		}

		pm.registerEvents(new SleepSkipEventListener(bootstrapper.getMessageService(), bootstrapper.getConfigService(), bootstrapper.getSleepService(), bootstrapper.getFlagsRepository(), bootstrapper.getBossBarService()), this);
		pm.registerEvents(new WorldChangeEventListener(bootstrapper.getSleepService()), this);
		pm.registerEvents(new PlayerBedLeaveEventListener(bootstrapper.getSleepService()), this);
		pm.registerEvents(new WorldLoadEventListener(bootstrapper.getBossBarService(), bootstrapper.getSleepMostWorldService()),this);
		pm.registerEvents(new WorldUnloadEventListener(bootstrapper.getBossBarService(), bootstrapper.getSleepMostWorldService()),this);
		pm.registerEvents(new PlayerSleepStateChangeEventListener(this, bootstrapper.getSleepService(), bootstrapper.getFlagsRepository(), bootstrapper.getBossBarService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService()), this);
		pm.registerEvents(new TimeCycleChangeEventListener(bootstrapper.getSleepService(), bootstrapper.getSleepMostWorldService(), bootstrapper.getFlagsRepository(), bootstrapper.getInsomniaService()),this );
		pm.registerEvents(new PlayerConsumeEventListener(bootstrapper.getSleepService(), bootstrapper.getInsomniaService(), bootstrapper.getMessageService(), bootstrapper.getFlagsRepository()), this);

		if(HookManager.isGSitInstalled()){
			getLogger().info("Hooked to GSit!");
			pm.registerEvents(new GSitEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(), bootstrapper.getFlagsRepository(), bootstrapper.getBossBarService(), bootstrapper.getSleepMostWorldService(), bootstrapper.getInsomniaService()),this);
		}

		Bukkit.getScheduler().runTaskAsynchronously(this, () -> notifyIfNewUpdateExists(bootstrapper.getUpdateService()));
		runPlayerTasks();
		runPreTimerTasks();
		runTimers(bootstrapper.getSleepService(), bootstrapper.getSleepMostWorldService(), bootstrapper.getInsomniaService());

	}
	
	public static Sleepmost getInstance() {
                return instance;
	}

	private void runPreTimerTasks(){
		for(World world: Bukkit.getWorlds()){
			this.bootstrapper.getSleepMostWorldService().registerWorld(world);
		}
	}

	private void runPlayerTasks(){
		for(Player p: Bukkit.getOnlinePlayers()){
			this.bootstrapper.getSleepMostPlayerService().registerNewPlayer(p);
		}
	}

	private void runTimers(ISleepService sleepService, ISleepMostWorldService sleepMostWorldService, IInsomniaService insomniaService){
		new Heartbeat(sleepService, sleepMostWorldService, insomniaService).runTaskTimer(this, 20,20);
	}

	private void notifyIfNewUpdateExists(IUpdateService updateService) 
	{
		if(updateService.hasUpdate())
			getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
	}

	void registerBossBars(){
		for(World world: Bukkit.getWorlds()){
			IBossBarService bossBarService = this.bootstrapper.getBossBarService();
			bossBarService.registerBossBar(world);
			bossBarService.setVisible(world, false);
		}
	}
}
