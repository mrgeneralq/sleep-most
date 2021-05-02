package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


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
        this.cachedUpdateVersion = updateRepository.getLatestVersion();
    }


    @Override
    public boolean hasUpdate() {
        return hasUpdate(this.getCurrentVersion());
    }

    @Override
    public boolean hasUpdate(String version) {

        if (!configService.updateCheckerEnabled()) {
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


        if (cachedUpdateVersion == null)
            return false;

        if (cachedUpdateVersion.equals(version))
            return false;

        List<Integer> splittedCurrentVersion = Arrays.stream(version.split("\\."))
                .map(Integer::parseInt)
                .collect(toList());

        List<Integer> splittedCachedVersion = Arrays.stream(this.cachedUpdateVersion.split("\\."))
                .map(Integer::parseInt)
                .collect(toList());

        equalizeSizes(splittedCachedVersion, splittedCurrentVersion);

        for(int i = 0; i < splittedCachedVersion.size(); i++){



            //can you print length of both variables? here
            //not here, but I already showed you in eclipse the lists themselves after equalizeSizes() ran
            //it 100% works
            //idk man,
            //there's something that we both miss in the logic... omg

            Integer current = splittedCurrentVersion.get(i);
            Integer cached = splittedCachedVersion.get(i);

           // CACHED  1.12.1
           // CURRENT 1.12.0

            /*
            1 false
            2 false
            3 true
             */

            //CACHED 2.2.0.0
            //CURRENT 1.1.5.4

            if(cached > current)
                return false;

                        /*
                testRemoteVersion =  "2.2.0.0";
                testCurrentVersion = "1.1.5.4

                //found the issue

                2 > 1 true
                1 > 2 false
                5 > 0 true





                1 > 2 = false
                12 > 12 = false
                0 > 1 = false

                = result update


             */


        }
        return true;
    }

    @Override
    public String getCurrentVersion() {
        return main.getDescription().getVersion();
    }

    @Override
    public String getCachedUpdateVersion(){
        return this.cachedUpdateVersion;
    }

    private static void equalizeSizes(List<Integer> splittedLocalVersion, List<Integer> splittedRemoteVersion)
    {
        int localSize = splittedLocalVersion.size();
        int cachedSize = splittedRemoteVersion.size();

        int zerosAmount = Math.max(localSize, cachedSize) - Math.min(localSize, cachedSize);

        for(int i = 1; i <= zerosAmount; i++)
        {
            if(localSize < cachedSize)
                splittedLocalVersion.add(0);
            else
                splittedRemoteVersion.add(0);
        }
    }
}
