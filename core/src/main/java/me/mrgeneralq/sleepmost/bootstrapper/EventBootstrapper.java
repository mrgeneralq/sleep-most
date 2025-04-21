package me.mrgeneralq.sleepmost.bootstrapper;

import com.google.inject.Inject;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.eventlisteners.*;
import me.mrgeneralq.sleepmost.eventlisteners.hooks.GSitEventListener;
import me.mrgeneralq.sleepmost.interfaces.IHookService;
import me.mrgeneralq.sleepmost.models.Hook;
import me.mrgeneralq.sleepmost.placeholderapi.PapiExtension;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Optional;

public class EventBootstrapper {

    private final Sleepmost plugin;
    private final IHookService hookService;
    private final EntitySpawnEventListener entitySpawn;
    private final EntityTargetLivingEntityEventListener entityTargetLivingEntity;
    private final PlayerBedEnterEventListener playerBedEnter;
    private final PlayerConsumeEventListener playerConsume;
    private final PlayerJoinEventListener playerJoin;
    private final PlayerQuitEventListener playerQuit;
    private final PlayerSleepStateChangeEventListener playerSleepStateChange;
    private final PlayerWorldChangeEventListener playerWorldChange;
    private final SleepSkipEventListener sleepSkip;
    private final TimeCycleChangeEventListener timeCycleChange;
    private final TimeSkipEventListener timeSkip;
    private final WorldChangeEventListener worldChange;
    private final WorldLoadEventListener worldLoad;
    private final WorldUnloadEventListener worldUnload;
    private final GSitEventListener gsitEventListener;


    @Inject
    public EventBootstrapper(
            Sleepmost plugin,
            IHookService hookService,
            EntitySpawnEventListener entitySpawnEventListener,
            EntityTargetLivingEntityEventListener entityTargetLivingEntityEventListener,
            PlayerBedEnterEventListener playerBedEnterEventListener,
            PlayerConsumeEventListener playerConsumeEventListener,
            PlayerJoinEventListener playerJoinEventListener,
            PlayerQuitEventListener playerQuitEventListener,
            PlayerSleepStateChangeEventListener playerSleepStateChangeEventListener,
            PlayerWorldChangeEventListener playerWorldChangeEventListener,
            SleepSkipEventListener sleepSkipEventListener,
            TimeCycleChangeEventListener timeCycleChangeEventListener,
            TimeSkipEventListener timeSkipEventListener,
            WorldChangeEventListener worldChangeEventListener,
            WorldLoadEventListener worldLoadEventListener,
            WorldUnloadEventListener worldUnloadEventListener,
            GSitEventListener gsitEventListener
    ) {
        this.plugin = plugin;
        this.hookService = hookService;
        this.entitySpawn = entitySpawnEventListener;
        this.entityTargetLivingEntity = entityTargetLivingEntityEventListener;
        this.playerBedEnter = playerBedEnterEventListener;
        this.playerConsume = playerConsumeEventListener;
        this.playerJoin = playerJoinEventListener;
        this.playerQuit = playerQuitEventListener;
        this.playerSleepStateChange = playerSleepStateChangeEventListener;
        this.playerWorldChange = playerWorldChangeEventListener;
        this.sleepSkip = sleepSkipEventListener;
        this.timeCycleChange = timeCycleChangeEventListener;
        this.timeSkip = timeSkipEventListener;
        this.worldChange = worldChangeEventListener;
        this.worldLoad = worldLoadEventListener;
        this.worldUnload = worldUnloadEventListener;
        this.gsitEventListener = gsitEventListener;
    }

    public void bootstrap() {

        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(entitySpawn, plugin);
        pluginManager.registerEvents(entityTargetLivingEntity, plugin);
        pluginManager.registerEvents(playerBedEnter, plugin);
        pluginManager.registerEvents(playerConsume, plugin);
        pluginManager.registerEvents(playerJoin, plugin);
        pluginManager.registerEvents(playerQuit, plugin);
        pluginManager.registerEvents(playerSleepStateChange, plugin);
        pluginManager.registerEvents(playerWorldChange, plugin);
        pluginManager.registerEvents(sleepSkip, plugin);
        pluginManager.registerEvents(timeCycleChange, plugin);

        if(ServerVersion.CURRENT_VERSION.hasTimeSkipEvent()){
            pluginManager.registerEvents(timeSkip, plugin);
        }

        pluginManager.registerEvents(worldChange, plugin);
        pluginManager.registerEvents(worldLoad, plugin);
        pluginManager.registerEvents(worldUnload, plugin);

        Optional<Hook> gsitHook = hookService.getHook(SleepMostHook.GSIT);
        if(gsitHook.isPresent()){
            pluginManager.registerEvents(gsitEventListener, plugin);
            Bukkit.getLogger().info("[sleep-most] GSit hook detected and registered GSit event listener");
        }

        Optional<Hook> placeholderAPIHook = moodTrapper.getHookService().getHook(SleepMostHook.PLACEHOLDER_API);
        if(placeholderAPIHook.isPresent()){
            new PapiExtension(this, moodTrapper.getFlagsRepository(), moodTrapper.getSleepService()).register();
        }

    }
}
