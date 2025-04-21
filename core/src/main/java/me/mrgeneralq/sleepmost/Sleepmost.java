package me.mrgeneralq.sleepmost;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.mrgeneralq.sleepmost.bootstrapper.Bootstrapper;
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
import me.mrgeneralq.sleepmost.modules.PluginModule;
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
import me.mrgeneralq.sleepmost.statics.Moodtrapper;
import java.util.Optional;

public class Sleepmost extends JavaPlugin {

	private static Sleepmost instance;
	private Moodtrapper moodTrapper;

	private IMessageService messageService;

	@Override
	public void onEnable() {

		Injector injector = Guice.createInjector(new PluginModule(this));
		Bootstrapper bootstrapper = injector.getInstance(Bootstrapper.class);
		bootstrapper.bootstrap();

		instance = this;
		saveDefaultConfig();

		//init metrics
		final int bStatsID = 6212;
		new Metrics(this, bStatsID);

		//load the messages at start
		MessageMapper.getMapper().loadMessages();
		
		//init bootstrapper
		this.moodTrapper = Moodtrapper.getBootstrapper();
		moodTrapper.initialize(this, Bukkit.getPluginManager());

		this.messageService = moodTrapper.getMessageService();

		//creating missing messages
		this.messageService.createMissingMessages();

		//check if boss bars are supported before registered
		if(ServerVersion.CURRENT_VERSION.supportsBossBars())
			this.registerBossBars();

		//init commands
		SleepmostCommand sleepmostCommand = new SleepmostCommand(moodTrapper.getSleepService(), moodTrapper.getMessageService(), moodTrapper.getUpdateService(), moodTrapper.getFlagService(), moodTrapper.getFlagsRepository(), moodTrapper.getConfigRepository(), moodTrapper.getCooldownService(), moodTrapper.getBossBarService(), moodTrapper.getSleepMostWorldService(), moodTrapper.getSleepMostPlayerService(), moodTrapper.getInsomniaService(), moodTrapper.getDebugService(), moodTrapper.getHookService());
		getCommand("sleepmost").setExecutor(sleepmostCommand);


		//REGISTER HOOKS
		registerHooks();

		Optional<Hook> gsitHook = moodTrapper.getHookService().getHook(SleepMostHook.GSIT);
		if(gsitHook.isPresent()){
			pm.registerEvents(new GSitEventListener(moodTrapper.getSleepService(), moodTrapper.getMessageService(), moodTrapper.getCooldownService(), moodTrapper.getFlagsRepository(), moodTrapper.getBossBarService(), moodTrapper.getSleepMostWorldService(), moodTrapper.getInsomniaService()),this);
			Bukkit.getLogger().info("[sleep-most] GSit hook detected and registered GSit event listener");
		}

		Optional<Hook> placeholderAPIHook = moodTrapper.getHookService().getHook(SleepMostHook.PLACEHOLDER_API);
		if(placeholderAPIHook.isPresent()){
			new PapiExtension(this, moodTrapper.getFlagsRepository(), moodTrapper.getSleepService()).register();
		}

		Bukkit.getScheduler().runTaskAsynchronously(this, () -> notifyIfNewUpdateExists(moodTrapper.getUpdateService()));
		runPlayerTasks();
		runPreTimerTasks();
		runTimers(moodTrapper.getSleepService(), moodTrapper.getSleepMostWorldService(), moodTrapper.getInsomniaService());

	}
	
	public static Sleepmost getInstance() {
                return instance;
	}

	private void runPreTimerTasks(){
		for(World world: Bukkit.getWorlds()){
			this.moodTrapper.getSleepMostWorldService().registerWorld(world);

			if(ServerVersion.CURRENT_VERSION.supportsGameRules() && this.moodTrapper.getSleepService().isEnabledAt(world)){
				if(this.moodTrapper.getFlagsRepository().getDisableDaylightcycleGamerule().getValueAt(world)){
					world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
				}
			}
		}
	}

	private void runPlayerTasks(){
		for(Player p: Bukkit.getOnlinePlayers()){
			this.moodTrapper.getSleepMostPlayerService().registerNewPlayer(p);
		}
	}

	private void runTimers(ISleepService sleepService, ISleepMostWorldService sleepMostWorldService, IInsomniaService insomniaService){
		new Heartbeat(sleepService, sleepMostWorldService, insomniaService, moodTrapper.getFlagsRepository()).runTaskTimer(this, 20,20);
	}

	private void notifyIfNewUpdateExists(IUpdateService updateService) 
	{
		if(updateService.hasUpdate())
			getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
	}

	void registerBossBars(){
		for(World world: Bukkit.getWorlds()){
			IBossBarService bossBarService = this.moodTrapper.getBossBarService();
			bossBarService.registerBossBar(world);
			bossBarService.setVisible(world, false);
		}
	}

	void registerHooks(){

		Hook superVanishHook = new SuperVanishHook();
		superVanishHook.addAlias("PremiumVanish");

		IHookService hookService = moodTrapper.getHookService();
		hookService.attemptRegister(superVanishHook);
		hookService.attemptRegister(new PlaceholderAPIHook());
		hookService.attemptRegister(new GsitHook());
		hookService.attemptRegister(new EssentialsHook());


	}
}
