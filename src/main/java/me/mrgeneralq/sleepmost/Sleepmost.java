package me.mrgeneralq.sleepmost;

import me.mrgeneralq.sleepmost.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.eventlisteners.*;
import me.mrgeneralq.sleepmost.eventlisteners.hooks.GSitEventListener;
import me.mrgeneralq.sleepmost.hooks.EssentialsHook;
import me.mrgeneralq.sleepmost.hooks.GsitHook;
import me.mrgeneralq.sleepmost.hooks.PlaceholderAPIHook;
import me.mrgeneralq.sleepmost.hooks.SuperVanishHook;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.mappers.MessageMapper;
import me.mrgeneralq.sleepmost.models.Hook;
import me.mrgeneralq.sleepmost.placeholderapi.PapiExtension;
import me.mrgeneralq.sleepmost.runnables.Heartbeat;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrgeneralq.sleepmost.commands.SleepmostCommand;
import me.mrgeneralq.sleepmost.statics.Bootstrapper;
import java.util.Optional;

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
		bootstrapper.initialize(this, Bukkit.getPluginManager());

		this.messageService = bootstrapper.getMessageService();

		//creating missing messages
		this.messageService.createMissingMessages();

		//check if boss bars are supported before registered
		if(ServerVersion.CURRENT_VERSION.supportsBossBars())
			this.registerBossBars();

		//init commands
		SleepmostCommand sleepmostCommand = new SleepmostCommand(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getUpdateService(), bootstrapper.getFlagService(), bootstrapper.getFlagsRepository(), bootstrapper.getConfigRepository(), bootstrapper.getCooldownService(), bootstrapper.getBossBarService(), bootstrapper.getSleepMostWorldService(), bootstrapper.getSleepMostPlayerService(), bootstrapper.getInsomniaService(), bootstrapper.getDebugService(), bootstrapper.getHookService());
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

		pm.registerEvents(new SleepSkipEventListener(bootstrapper.getMessageService(), bootstrapper.getConfigService(), bootstrapper.getSleepService(), bootstrapper.getFlagsRepository(), bootstrapper.getBossBarService(), bootstrapper.getHookService()), this);
		pm.registerEvents(new WorldChangeEventListener(bootstrapper.getSleepService()), this);
		pm.registerEvents(new PlayerBedLeaveEventListener(bootstrapper.getSleepService()), this);
		pm.registerEvents(new WorldLoadEventListener(bootstrapper.getBossBarService(), bootstrapper.getSleepMostWorldService()),this);
		pm.registerEvents(new WorldUnloadEventListener(bootstrapper.getBossBarService(), bootstrapper.getSleepMostWorldService()),this);
		pm.registerEvents(new PlayerSleepStateChangeEventListener(this, bootstrapper.getSleepService(), bootstrapper.getFlagsRepository(), bootstrapper.getBossBarService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(), bootstrapper.getSleepMostWorldService()), this);
		pm.registerEvents(new TimeCycleChangeEventListener(bootstrapper.getSleepService(), bootstrapper.getSleepMostWorldService(), bootstrapper.getFlagsRepository(), bootstrapper.getInsomniaService()),this );
		pm.registerEvents(new PlayerConsumeEventListener(bootstrapper.getSleepService(), bootstrapper.getInsomniaService(), bootstrapper.getMessageService(), bootstrapper.getFlagsRepository()), this);

		//REGISTER HOOKS
		registerHooks();

		Optional<Hook> gsitHook = bootstrapper.getHookService().getHook(SleepMostHook.GSIT);
		if(gsitHook.isPresent()){
			pm.registerEvents(new GSitEventListener(bootstrapper.getSleepService(), bootstrapper.getMessageService(), bootstrapper.getCooldownService(), bootstrapper.getFlagsRepository(), bootstrapper.getBossBarService(), bootstrapper.getSleepMostWorldService(), bootstrapper.getInsomniaService()),this);
			Bukkit.getLogger().info("[sleep-most] GSit hook detected and registered GSit event listener");
		}

		Optional<Hook> placeholderAPIHook = bootstrapper.getHookService().getHook(SleepMostHook.PLACEHOLDER_API);
		if(placeholderAPIHook.isPresent()){
			new PapiExtension(this, bootstrapper.getFlagsRepository(), bootstrapper.getSleepService()).register();
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

			if(ServerVersion.CURRENT_VERSION.supportsGameRules() && this.bootstrapper.getSleepService().isEnabledAt(world)){
				if(this.bootstrapper.getFlagsRepository().getDisableDaylightcycleGamerule().getValueAt(world)){
					world.setGameRule(GameRule.ADVANCE_TIME, true);
				}
			}
		}
	}

	private void runPlayerTasks(){
		for(Player p: Bukkit.getOnlinePlayers()){
			this.bootstrapper.getSleepMostPlayerService().registerNewPlayer(p);
		}
	}

	private void runTimers(ISleepService sleepService, ISleepMostWorldService sleepMostWorldService, IInsomniaService insomniaService){
		new Heartbeat(sleepService, sleepMostWorldService, insomniaService, bootstrapper.getFlagsRepository()).runTaskTimer(this, 20,20);
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

	void registerHooks(){

		Hook superVanishHook = new SuperVanishHook();
		superVanishHook.addAlias("PremiumVanish");

		IHookService hookService = bootstrapper.getHookService();
		hookService.attemptRegister(superVanishHook);
		hookService.attemptRegister(new PlaceholderAPIHook());
		hookService.attemptRegister(new GsitHook());
		hookService.attemptRegister(new EssentialsHook());


	}
}
