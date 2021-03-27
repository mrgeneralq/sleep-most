package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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


        if(cachedUpdateVersion.equals(getCurrentVersion()))
            return false;

        try{

            List<Integer> splittedCurrentVersion = Arrays.stream(getCurrentVersion().split("\\.")).map(Integer::parseInt).collect(Collectors.toList());
            List<Integer> splittedCachedVersion = Arrays.stream(this.cachedUpdateVersion.split("\\.")).map(Integer::parseInt).collect(Collectors.toList());


            for(int i = 0; i < splittedCachedVersion.size(); i ++){

                Integer current = splittedCurrentVersion.get(i);
                Integer cached = splittedCachedVersion.get(i);

                if(current > cached)
                    return false;

            }

            return true;

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
