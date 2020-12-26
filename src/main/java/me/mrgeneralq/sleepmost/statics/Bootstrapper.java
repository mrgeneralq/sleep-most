package me.mrgeneralq.sleepmost.statics;

import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.repositories.ConfigRepository;
import me.mrgeneralq.sleepmost.repositories.CooldownRepository;
import me.mrgeneralq.sleepmost.repositories.UpdateRepository;
import me.mrgeneralq.sleepmost.services.*;
import me.mrgeneralq.sleepmost.Sleepmost;

public class Bootstrapper {

    private Sleepmost main;
    private static Bootstrapper instance;
    private ISleepService sleepService;
    private IMessageService messageService;
    private IConfigRepository configRepository;
    private ConfigMessageMapper configMessageMapper;
    private ICooldownService cooldownService;
    private ICooldownRepository cooldownRepository;
    private IUpdateService updateService;
    private IUpdateRepository updateRepository;
    private ISleepFlagService sleepFlagService;

    //new service logic
    private IConfigService configService;

    public IConfigRepository getConfigRepository() {
        return configRepository;
    }

    public IMessageService getMessageService() {
        return messageService;
    }

    private Bootstrapper(){ }

    public void initialize(Sleepmost main){
        this.main = main;

        this.configService = new ConfigService(main);
        this.configRepository = new ConfigRepository(main);
        this.cooldownRepository = new CooldownRepository();
        this.updateRepository = new UpdateRepository("60623");
        this.sleepFlagService = new SleepFlagService(this.getConfigRepository());
        this.configService = new ConfigService(main);

        this.updateService = new UpdateService(this.getUpdateRepository(), main, configService);
        this.sleepService = new SleepService(configService, sleepFlagService , this.getConfigRepository());
        this.cooldownService = new CooldownService(this.getCooldownRepository(), this.getConfigRepository());
        this.messageService = new MessageService(this.getConfigRepository(), this.getSleepService());
        this.configMessageMapper = ConfigMessageMapper.getMapper();


        // initialize for singleton
        configMessageMapper.initialize(main);
    }

    public static Bootstrapper getBootstrapper(){

        if(instance == null)
            instance = new Bootstrapper();
        return instance;
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

    public ISleepFlagService getSleepFlagService() {
        return sleepFlagService;
    }

    public IConfigService getConfigService() {
        return configService;
    }
}
