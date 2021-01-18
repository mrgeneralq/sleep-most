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
    private IFlagService flagService;
    private IConfigService configService;

    private static Bootstrapper instance;

    private Bootstrapper(){ }

    public void initialize(Sleepmost main){

        //repos
        this.updateRepository = new UpdateRepository("60623");
        this.cooldownRepository = new CooldownRepository();
        this.configRepository = new ConfigRepository(main);
        this.flagsRepository = new FlagsRepository(configRepository);

        this.messageService = new MessageService(configRepository, sleepService);
        this.flagService = new FlagService(flagsRepository, configRepository, configService, messageService);

        //services
        this.configService = new ConfigService(main);
        this.updateService = new UpdateService(updateRepository, main, configService);
        this.cooldownService = new CooldownService(cooldownRepository, configRepository);
        this.sleepService = new SleepService(main, configService, configRepository, messageService, flagsRepository, cooldownService, flagService);

        //mappers
        this.configMessageMapper = ConfigMessageMapper.getMapper();

        //independent inits
        this.configMessageMapper.initialize(main);
        this.flagService.reportIllegalValues();
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
}
