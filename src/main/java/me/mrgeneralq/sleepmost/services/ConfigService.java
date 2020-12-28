package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;
import org.bukkit.Sound;

import static me.mrgeneralq.sleepmost.statics.ChatColorUtils.colorize;

public class ConfigService implements IConfigService {

    private final Sleepmost main;

    public ConfigService(Sleepmost main) {
        this.main = main;
    }

    @Override
    public int getResetTime() {
        return main.getConfig().getInt("time-after-reset");
    }

    @Override
    public boolean updateCheckerEnabled() {
        return main.getConfig().getBoolean("update-checker-enabled");
    }


    @Override
    public boolean getTitleNightSkippedEnabled() {
        return main.getConfig().getBoolean("titles.night-skipped.enabled");
    }

    @Override
    public String getTitleNightSkippedTitle() {
        return colorize(main.getConfig().getString("titles.night-skipped.title"));
    }

    @Override
    public String getTitleNightSkippedSubTitle() {
        return colorize(main.getConfig().getString("titles.night-skipped.subtitle"));
    }

    @Override
    public boolean getTitleStormSkippedEnabled() {
        return main.getConfig().getBoolean("titles.storm-skipped.enabled");
    }

    @Override
    public String getTitleStormSkippedTitle() {
        return colorize(main.getConfig().getString("titles.storm-skipped.title"));
    }

    @Override
    public String getTitleStormSkippedSubTitle() {
        return colorize(main.getConfig().getString("titles.storm-skipped.subtitle"));
    }


    @Override
    public boolean getSoundNightSkippedEnabled(){
        return main.getConfig().getBoolean("sounds.night-skipped.enabled");
    }

    @Override
    public Sound getSoundNightSkippedSound(){

        try{
            String soundName = main.getConfig().getString("sounds.night-skipped.sound");
            return Sound.valueOf(soundName);
        }catch(Exception ex){
            return null;
        }

    }

    @Override
    public boolean getSoundStormSkippedEnabled(){
        return main.getConfig().getBoolean("sounds.storm-skipped.enabled");
    }


    @Override
    public Sound getSoundStormSkippedSound(){
        try{
            String soundName = main.getConfig().getString("sounds.storm-skipped.sound");
            return Sound.valueOf(soundName);
        }catch(Exception ex){
            return null;
        }
    }

}
