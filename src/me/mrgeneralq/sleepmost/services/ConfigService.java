package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;

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
