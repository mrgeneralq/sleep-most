package me.mrgeneralq.sleepmost.bootstrapper;

import com.google.inject.Inject;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.commands.SleepmostCommand;
import me.mrgeneralq.sleepmost.enums.SleepMostHook;
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
import me.mrgeneralq.sleepmost.statics.Moodtrapper;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Optional;

public class Bootstrapper {

    private final Sleepmost plugin;
    private final EventBootstrapper eventBootstrapper;
    private final CommandBootstrapper commandBootstrapper;
    private final IMessageService messageService;
    private final IHookService hookService;

    @Inject
    public Bootstrapper(
            Sleepmost plugin,
            EventBootstrapper eventListenerBootstrapper,
            CommandBootstrapper commandBootstrapper,
            IMessageService messageService,
            IHookService hookService,
    ) {
        this.plugin = plugin;
        this.eventBootstrapper = eventListenerBootstrapper;
        this.commandBootstrapper = commandBootstrapper;
        this.messageService = messageService;
        this.hookService = hookService;
    }

    public void bootstrap() {
        System.out.println("Bootstrapping the application...");

        eventBootstrapper.bootstrap();
        commandBootstrapper.bootstrap();


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


        //REGISTER HOOKS
        registerHooks();





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
}
