package me.mrgeneralq.sleepmost.statics;

import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.messages.MessageService;
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

        //config
        this.configService = new ConfigService(main);
        this.configRepository = new ConfigRepository(main);
        this.configMessageMapper = ConfigMessageMapper.getMapper();
        this.configMessageMapper.initialize(main);

        //update
        this.updateRepository = new UpdateRepository("60623");
        this.updateService = new UpdateService(updateRepository, main, configService);

        //sleep
        this.sleepService = new SleepService(configService, configRepository);

        //cooldown
        this.cooldownRepository = new CooldownRepository();
        this.cooldownService = new CooldownService(cooldownRepository, configRepository);

        //messages
        this.messageService = new MessageService(configRepository, sleepService);
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

    public IConfigService getConfigService() {
        return configService;
    }
}
