package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;

import java.util.Arrays;
import java.util.List;

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

        List<Integer> splitCurrentVersion = getComponents(localVersion);
        List<Integer> splitLatestVersion = getComponents(latestVersion);

        equalizeSizes(splitLatestVersion, splitCurrentVersion);

        for (int i = 0; i < splitLatestVersion.size(); i++) {
            Integer current = splitCurrentVersion.get(i);
            Integer latest = splitLatestVersion.get(i);

            // latest is bigger
            if (latest > current)
                return true;

            // latest is lower
            if (latest < current)
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

    private static List<Integer> getComponents(String version) {
        return Arrays.stream(version.split("\\."))
                .map(Integer::parseInt)
                .collect(toList());
    }

    private static void equalizeSizes(List<Integer> splitLocalVersion, List<Integer> splitRemoteVersion) {
        int localSize = splitLocalVersion.size();
        int cachedSize = splitRemoteVersion.size();

        int zerosAmount = Math.max(localSize, cachedSize) - Math.min(localSize, cachedSize);
        List<Integer> smallerList = localSize < cachedSize ? splitLocalVersion : splitRemoteVersion;

        for (int i = 1; i <= zerosAmount; i++)
            smallerList.add(0);
    }
}
