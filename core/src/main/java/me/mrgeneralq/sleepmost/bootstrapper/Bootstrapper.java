package me.mrgeneralq.sleepmost.bootstrapper;

import com.google.inject.Inject;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.mappers.MessageMapper;
import me.mrgeneralq.sleepmost.runnables.Heartbeat;
import me.mrgeneralq.sleepmost.services.*;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Bootstrapper {

    private final Sleepmost plugin;
    private final EventBootstrapper eventBootstrapper;
    private final CommandBootstrapper commandBootstrapper;
    private final HookBootstrapper hookBootstrapper;
    private final IMessageService messageService;
    private final IHookService hookService;
    private final Heartbeat heartbeat;
    private final ISleepMostWorldService sleepMostWorldService;
    private final IUpdateService updateService;
    private final ISleepService sleepService;
    private final ISleepMostPlayerService sleepMostPlayerService;
    private final IBossBarService bossBarService;

    @Inject
    public Bootstrapper(
            Sleepmost plugin,
            EventBootstrapper eventListenerBootstrapper,
            CommandBootstrapper commandBootstrapper,
            HookBootstrapper hookBootstrapper,
            IMessageService messageService,
            IHookService hookService,
            Heartbeat heartbeat,
            ISleepMostWorldService sleepMostWorldService,
            IUpdateService updateService,
            ISleepService sleepService,
            ISleepMostPlayerService sleepMostPlayerService,
            IBossBarService bossBarService
    ) {
        this.plugin = plugin;
        this.eventBootstrapper = eventListenerBootstrapper;
        this.commandBootstrapper = commandBootstrapper;
        this.hookBootstrapper = hookBootstrapper;
        this.messageService = messageService;
        this.hookService = hookService;
        this.heartbeat = heartbeat;
        this.sleepMostWorldService = sleepMostWorldService;
        this.updateService = updateService;
        this.sleepService = sleepService;
        this.sleepMostPlayerService = sleepMostPlayerService;
        this.bossBarService = bossBarService;
    }

    public void bootstrap() {
        System.out.println("Bootstrapping the application...");

        eventBootstrapper.bootstrap();
        commandBootstrapper.bootstrap();
        hookBootstrapper.bootstrap();


        plugin.saveDefaultConfig();

        //init metrics
        final int bStatsID = 6212;
        new Metrics(plugin, bStatsID);

        //load the messages at start
        MessageMapper.getMapper().loadMessages();

        //creating missing messages
        this.messageService.createMissingMessages();

        //check if boss bars are supported before registered
        if(ServerVersion.CURRENT_VERSION.supportsBossBars())
            this.registerBossBars();



        runTimers();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> notifyIfNewUpdateExists(this.updateService));
        runPlayerTasks();
        runPreTimerTasks();

    }

    private void runPreTimerTasks(){
        for(World world: Bukkit.getWorlds()){
            this.sleepMostWorldService.registerWorld(world);

            if(ServerVersion.CURRENT_VERSION.supportsGameRules() && this.sleepService.isEnabledAt(world)){
                if(this.moodTrapper.getFlagsRepository().getDisableDaylightcycleGamerule().getValueAt(world)){
                    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                }
            }
        }
    }

    private void runPlayerTasks(){
        for(Player p: Bukkit.getOnlinePlayers()){
            this.sleepMostPlayerService.registerNewPlayer(p);
        }
    }

    void registerBossBars(){
        for(World world: Bukkit.getWorlds()){
            this.bossBarService.registerBossBar(world);
            this.bossBarService.setVisible(world, false);
        }
    }

    private void runTimers(){
        this.heartbeat.runTaskTimer(plugin, 20, 20);

    }

    private void notifyIfNewUpdateExists(IUpdateService updateService)
    {
        if(updateService.hasUpdate())
            Bukkit.getLogger().info("UPDATE FOUND: A newer version of sleep-most is available to download!");
    }

}
