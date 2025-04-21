package me.mrgeneralq.sleepmost.statics;

import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.mappers.ConfigMessageMapper;
import me.mrgeneralq.sleepmost.services.MessageService;
import me.mrgeneralq.sleepmost.repositories.*;
import me.mrgeneralq.sleepmost.services.*;
import me.mrgeneralq.sleepmost.Sleepmost;
import org.bukkit.plugin.PluginManager;

public class Moodtrapper {

    private ISleepService sleepService;
    private IMessageService messageService;
    private IConfigRepository configRepository;
    private ConfigMessageMapper configMessageMapper;
    private ICooldownService cooldownService;
    private ICooldownRepository cooldownRepository;
    private IUpdateService updateService;
    private IUpdateRepository updateRepository;
    private IFlagsRepository flagsRepository;
    private IFlagService flagService;
    private IConfigService configService;
    private BossBarRepository bossBarRepository;
    private IBossBarService bossBarService;
    private IPlayerService playerService;
    private ISleepMostPlayerService sleepMostPlayerService;
    private IInsomniaService insomniaService;
    private IDebugService debugService;

    private ISleepMostWorldService sleepMostWorldService;
    private IHookService hookService;

    private static Moodtrapper instance;

    private Moodtrapper(){ }

    public void initialize(Sleepmost main, PluginManager pluginManager){

        this.hookService = new HookService(pluginManager ,new HookRepository());


        this.configRepository = new ConfigRepository(main);
        this.configService = new ConfigService(main);
        this.sleepMostWorldService = new SleepMostWorldService(new SleepMostWorldRepository());


        this.sleepMostPlayerService = new SleepMostPlayerService(new SleepMostPlayerRepository());

        //do not move lower. Debugging required in several spots
        this.debugService = new DebugService(this.sleepMostPlayerService, this.configService);

        this.insomniaService = new InsomniaService(this.sleepMostPlayerService);

        this.flagsRepository = new FlagsRepository(hookService ,configRepository);

        this.messageService = new MessageService(configRepository, new MessageRepository(), flagsRepository);

        this.updateRepository = new UpdateRepository("60623");
        this.updateService = new UpdateService(updateRepository, main, configService);

        this.cooldownRepository = new CooldownRepository();
        this.cooldownService = new CooldownService(cooldownRepository, configRepository);

        this.flagService = new FlagService(flagsRepository, configRepository, configService, messageService, hookService);

        this.playerService = new PlayerService();
        this.sleepService = new SleepService(main, configService, configRepository, flagsRepository, flagService, playerService, debugService, sleepMostWorldService, hookService, messageService);

        this.configMessageMapper = ConfigMessageMapper.getMapper();
        this.configMessageMapper.initialize(main);

        //check if boss bars are supported
        if(ServerVersion.CURRENT_VERSION.supportsBossBars()){
        this.bossBarRepository = new BossBarRepository();
        this.bossBarService = new BossBarService(this.bossBarRepository);
        }


        //setups
        this.flagService.handleProblematicFlags();
    }

    public static Moodtrapper getBootstrapper(){

        if(instance == null)
            instance = new Moodtrapper();
        return instance;
    }

    public IConfigRepository getConfigRepository() {
        return configRepository;
    }

    public IMessageService getMessageService() {
        return messageService;
    }

    public ConfigMessageMapper getConfigMessageMapper() {
        return this.configMessageMapper;
    }

    public ICooldownService getCooldownService(){
        return this.cooldownService;
    }

    public ICooldownRepository getCooldownRepository(){
        return this.cooldownRepository;
    }

    public ISleepService getSleepService() {
        return sleepService;
    }

    public IUpdateService getUpdateService() {
        return updateService;
    }

    public IUpdateRepository getUpdateRepository() {
        return updateRepository;
    }

    public IConfigService getConfigService() {
        return configService;
    }
    public IFlagsRepository getFlagsRepository(){
        return this.flagsRepository;
    }
    public IFlagService getFlagService() {
        return this.flagService;
    }

    public BossBarRepository getBossBarRepository() {
        return bossBarRepository;
    }

    public IBossBarService getBossBarService() {
        return bossBarService;
    }

    public IPlayerService getPlayerService() {
        return playerService;
    }

    public ISleepMostPlayerService getSleepMostPlayerService() {
        return sleepMostPlayerService;
    }

    public IInsomniaService getInsomniaService() {
        return insomniaService;
    }

    public IDebugService getDebugService() {
        return debugService;
    }

    public ISleepMostWorldService getSleepMostWorldService() {
        return sleepMostWorldService;
    }

    public IHookService getHookService() {
        return hookService;
    }
}
