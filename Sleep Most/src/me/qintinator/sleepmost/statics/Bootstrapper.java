package me.qintinator.sleepmost.statics;

import me.qintinator.sleepmost.Main;
import me.qintinator.sleepmost.interfaces.*;
import me.qintinator.sleepmost.repositories.ConfigRepository;
import me.qintinator.sleepmost.repositories.CooldownRepository;
import me.qintinator.sleepmost.services.CooldownService;
import me.qintinator.sleepmost.services.MessageService;
import me.qintinator.sleepmost.services.SleepService;

public class Bootstrapper {

    private  Main main;
    private static Bootstrapper instance;
    private ISleepService sleepService;
    private IMessageService messageService;
    private IConfigRepository configRepository;
    private ConfigMessageMapper configMessageMapper;
    private ICooldownService cooldownService;
    private ICooldownRepository cooldownRepository;

    public IConfigRepository getConfigRepository() {
        return configRepository;
    }

    public IMessageService getMessageService() {
        return messageService;
    }

    private Bootstrapper(){ }

    public void initialize(Main main){
        main = main;

        this.configRepository = new ConfigRepository(main);
        this.cooldownRepository = new CooldownRepository();

        this.sleepService = new SleepService(this.getConfigRepository());
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
}
