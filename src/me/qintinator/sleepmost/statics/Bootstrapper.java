package me.qintinator.sleepmost.statics;

import me.qintinator.sleepmost.Main;
import me.qintinator.sleepmost.interfaces.*;
import me.qintinator.sleepmost.repositories.ConfigRepository;
import me.qintinator.sleepmost.repositories.CooldownRepository;
import me.qintinator.sleepmost.repositories.UpdateRepository;
import me.qintinator.sleepmost.services.*;

public class Bootstrapper {

    private  Main main;
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
    private ConfigService configService;

    public IConfigRepository getConfigRepository() {
        return configRepository;
    }

    public IMessageService getMessageService() {
        return messageService;
    }

    private Bootstrapper(){ }

    public void initialize(Main main){
        main = main;

        this.configService = new ConfigService(main);

        this.configRepository = new ConfigRepository(main);
        this.cooldownRepository = new CooldownRepository();
        this.updateRepository = new UpdateRepository();
        this.sleepFlagService = new SleepFlagService(this.getConfigRepository());
        this.configService = new ConfigService(main);

        this.updateService = new UpdateService(this.getUpdateRepository(), main);
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
}
