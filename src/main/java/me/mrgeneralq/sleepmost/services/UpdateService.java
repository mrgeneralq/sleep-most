package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import org.omg.SendingContext.RunTime;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class UpdateService implements IUpdateService {

    private final IUpdateRepository updateRepository;
    private final Sleepmost main;
    private final IConfigService configService;

    public UpdateService(IUpdateRepository updateRepository, Sleepmost main, IConfigService configService) {
        this.updateRepository = updateRepository;
        this.main = main;
        this.configService = configService;
    }

    @Override
    public boolean hasUpdate() {

        return hasUpdate(this.getCurrentVersion());
    }

    @Override
    public boolean hasUpdate(String localVersion) {

        if (!configService.updateCheckerEnabled())
            return false;

        String latestVersion = updateRepository.getLatestVersion();

        if (latestVersion.equals(localVersion))
            return false;

        List<Integer> splittedCurrentVersion = getComponents(localVersion);
        List<Integer> splittedCachedVersion = getComponents(latestVersion);

        equalizeSizes(splittedCachedVersion, splittedCurrentVersion);

        for (int i = 0; i < splittedCachedVersion.size(); i++) {

            Integer current = splittedCurrentVersion.get(i);
            Integer cached = splittedCachedVersion.get(i);

            // cached is bigger
            if (cached > current)
                return true;

            // cached is lower
            if (cached < current)
                return false;
        }
        return false;
    }

    @Override
    public String getCurrentVersion() {
        return main.getDescription().getVersion();
    }

    @Override
    public String getCachedUpdateVersion() {
        return updateRepository.getLatestVersion();
    }

    private static List<Integer> getComponents(String version){
        return Arrays.stream(version.split("\\."))
                .map(Integer::parseInt)
                .collect(toList());
    }

    private static void equalizeSizes(List<Integer> splittedLocalVersion, List<Integer> splittedRemoteVersion) {
        int localSize = splittedLocalVersion.size();
        int cachedSize = splittedRemoteVersion.size();

        int zerosAmount = Math.max(localSize, cachedSize) - Math.min(localSize, cachedSize);
        List<Integer> smallerList = localSize < cachedSize ? splittedLocalVersion : splittedRemoteVersion;

        for (int i = 1; i <= zerosAmount; i++)
            smallerList.add(0);
    }
}
