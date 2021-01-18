package me.mrgeneralq.sleepmost.statics;

import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.messages.MessageService;
import me.mrgeneralq.sleepmost.repositories.ConfigRepository;
import me.mrgeneralq.sleepmost.repositories.CooldownRepository;
import me.mrgeneralq.sleepmost.repositories.FlagsRepository;
import me.mrgeneralq.sleepmost.repositories.UpdateRepository;
import me.mrgeneralq.sleepmost.services.*;
import me.mrgeneralq.sleepmost.Sleepmost;

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
    private IFlagService flagsService;
    private IConfigService configService;

    private static Bootstrapper instance;

    private Bootstrapper(){ }

    public void initialize(Sleepmost main){

        //repos
        this.updateRepository = new UpdateRepository("60623");
        this.cooldownRepository = new CooldownRepository();
        this.configRepository = new ConfigRepository(main);
        this.flagsRepository = new FlagsRepository(this.configRepository);

        //services
        this.configService = new ConfigService(main);
        this.updateService = new UpdateService(this.updateRepository, main, this.configService);
        this.cooldownService = new CooldownService(this.cooldownRepository, this.configRepository);
        this.sleepService = new SleepService(main, this.configService, this.configRepository, this.messageService, this.flagsRepository, this.cooldownService, this.flagsService);

        this.messageService = new MessageService(this.configRepository, this.sleepService);
        this.flagsService = new FlagService(this.flagsRepository, this.configRepository, this.configService, this.messageService);

        //mappers
        this.configMessageMapper = ConfigMessageMapper.getMapper();

        //independent inits
        this.configMessageMapper.initialize(main);
        this.flagsService.reportIllegalValues();
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
    public IFlagService getFlagsService() {
        return this.flagsService;
    }
}
