package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import org.bukkit.Bukkit;

import java.util.Date;


public class UpdateService implements IUpdateService {

    private String cachedUpdateVersion;
    private final IUpdateRepository updateRepository;
    private final Sleepmost main;
    private final IConfigService configService;
    private Date lastChecked;

    public UpdateService(IUpdateRepository updateRepository, Sleepmost main, IConfigService configService) {

        this.updateRepository = updateRepository;
        this.main = main;
        this.configService = configService;
    }

    @Override
    public boolean hasUpdate() {

        if(!configService.updateCheckerEnabled()){
            return false;
        }

        if (lastChecked == null) {
            lastChecked = new Date();
        }

        if (cachedUpdateVersion == null) {
            cachedUpdateVersion = updateRepository.getLatestVersion();
        }

        double difference = (new Date().getTime() - lastChecked.getTime());

        if (difference > 7200000) {
            lastChecked = new Date();
            cachedUpdateVersion = updateRepository.getLatestVersion();
        }

        if(cachedUpdateVersion == null)
        	return false;


        try{
            int currentVersion = Integer.parseInt(getCurrentVersion().replaceAll("\\.",""));
            int cachedVersion = Integer.parseInt(this.cachedUpdateVersion.replaceAll("\\.",""));

            return cachedVersion > currentVersion;

        }catch(Exception ex){
            return false;
        }
    }

    @Override
    public String getCurrentVersion() {
        return main.getDescription().getVersion();
    }

    @Override
    public String getCachedUpdateVersion(){
        return this.cachedUpdateVersion;
    }

}
