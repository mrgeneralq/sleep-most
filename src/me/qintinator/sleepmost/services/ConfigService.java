package me.qintinator.sleepmost.services;

import me.qintinator.sleepmost.Main;

public class ConfigService {

    private final Main main;


    public ConfigService(Main main){
        this.main = main;
    }

    public int getResetTime(){
        return main.getConfig().getInt("time-after-reset");
    }

}
