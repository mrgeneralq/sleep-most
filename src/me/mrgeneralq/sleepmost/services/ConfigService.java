package me.qintinator.sleepmost.services;

import me.qintinator.sleepmost.Sleepmost;
import me.qintinator.sleepmost.interfaces.IConfigService;

public class ConfigService implements IConfigService {

    private final Sleepmost main;


    public ConfigService(Sleepmost main){
        this.main = main;
    }

    @Override
    public int getResetTime(){
        return main.getConfig().getInt("time-after-reset");
    }

    @Override
    public boolean updateCheckerEnabled() {
        return main.getConfig().getBoolean("update-checker-enabled");
    }
}
