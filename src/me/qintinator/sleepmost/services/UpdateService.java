package me.qintinator.sleepmost.services;

import me.qintinator.sleepmost.Sleepmost;
import me.qintinator.sleepmost.interfaces.IConfigService;
import me.qintinator.sleepmost.interfaces.IUpdateRepository;
import me.qintinator.sleepmost.interfaces.IUpdateService;
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

            Bukkit.broadcastMessage("Update checker is not enabled");
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

        return !main.getDescription().getVersion().equalsIgnoreCase(cachedUpdateVersion);
    }

    @Override
    public String getCurrentVersion() {
        return main.getDescription().getVersion();
    }

}
