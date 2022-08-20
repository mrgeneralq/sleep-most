package me.mrgeneralq.sleepmost.statics;

import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.mappers.ConfigMessageMapper;
import me.mrgeneralq.sleepmost.services.MessageService;
import me.mrgeneralq.sleepmost.placeholderapi.PapiExtension;
import me.mrgeneralq.sleepmost.repositories.*;
import me.mrgeneralq.sleepmost.services.*;
import me.mrgeneralq.sleepmost.Sleepmost;
import org.bukkit.Bukkit;

public class Bootstrapper {

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

    private WorldPropertyRepository worldPropertyRepository;
    private IWorldPropertyService worldPropertyService;

    private MessageRepository messageRepository;


    private static Bootstrapper instance;

    private Bootstrapper(){ }

    public void initialize(Sleepmost main){


        this.sleepMostPlayerService = new SleepMostPlayerService(new SleepMostPlayerRepository());

        this.configRepository = new ConfigRepository(main);
        this.configService = new ConfigService(main);
        this.flagsRepository = new FlagsRepository(configRepository);

        this.messageRepository = new MessageRepository();
        this.messageService = new MessageService(configRepository, messageRepository, flagsRepository);

        this.updateRepository = new UpdateRepository("60623");
        this.updateService = new UpdateService(updateRepository, main, configService);

        this.cooldownRepository = new CooldownRepository();
        this.cooldownService = new CooldownService(cooldownRepository, configRepository);


        this.flagService = new FlagService(flagsRepository, configRepository, configService, messageService);

        this.playerService = new PlayerService();

        this.sleepService = new SleepService(main, configService, configRepository, flagsRepository, flagService, playerService);

        this.worldPropertyRepository = new WorldPropertyRepository();
        this.worldPropertyService = new WorldPropertyService(this.worldPropertyRepository);

        this.configMessageMapper = ConfigMessageMapper.getMapper();
        this.configMessageMapper.initialize(main);



        //check if boss bars are supported
        if(ServerVersion.CURRENT_VERSION.supportsBossBars()){
        this.bossBarRepository = new BossBarRepository();
        this.bossBarService = new BossBarService(this.bossBarRepository);
        }

        //setups
        this.flagService.handleProblematicFlags();

        //setup PAPI

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PapiExtension(main, flagsRepository,sleepService).register();
        }

    }

    public static Bootstrapper getBootstrapper(){

        if(instance == null)
            instance = new Bootstrapper();
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

    public IWorldPropertyService getWorldPropertyService() {
        return worldPropertyService;
    }

    public IPlayerService getPlayerService() {
        return playerService;
    }

    public ISleepMostPlayerService getSleepMostPlayerService() {
        return sleepMostPlayerService;
    }
}
