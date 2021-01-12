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
    private IFlagsRepository flagsRepository;
    private IFlagService flagService;
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
        this.updateService = new UpdateService(this.updateRepository, main, this.configService);

        //cooldown
        this.cooldownRepository = new CooldownRepository();
        this.cooldownService = new CooldownService(this.cooldownRepository, this.configRepository);

        //messages
        this.messageService = new MessageService(this.configRepository, this.sleepService);

        //flags
        this.flagsRepository = new FlagsRepository(this.configRepository, this.configService, this.messageService);
        this.flagService = new FlagService(this.flagsRepository, this.configRepository, this.configService, this.messageService);
        this.flagService.reportIllegalValues();

        //sleep
        this.sleepService = new SleepService(this.configService, this.configRepository, this.flagsRepository.getCalculationMethodFlag(), this.flagsRepository.getPlayersRequiredFlag(), this.flagsRepository.getUseAfkFlag());
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
    public IFlagsRepository getFlagsRepository(){
        return this.flagsRepository;
    }
    public IFlagService getFlagService() {
        return this.flagService;
    }
}
