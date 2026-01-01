package me.mrgeneralq.sleepmost.core.services;

import me.mrgeneralq.sleepmost.core.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.core.Sleepmost;
import me.mrgeneralq.sleepmost.core.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.core.interfaces.IUpdateService;
import me.mrgeneralq.sleepmost.core.utils.Version;

public class UpdateService implements IUpdateService {

    private final Sleepmost main;
    private final IConfigService configService;
    private final IUpdateRepository updateRepository;

    public UpdateService(IUpdateRepository updateRepository, Sleepmost main, IConfigService configService) {
        this.main = main;
        this.configService = configService;
        this.updateRepository = updateRepository;
    }

    @Override
    public boolean hasUpdate() {
        return hasUpdate(main.getDescription().getVersion());
    }

    @Override
    public boolean hasUpdate(String localVersion) {
        if (!configService.updateCheckerEnabled())
            return false;

        String latestVersion = updateRepository.getLatestVersion();

        if (latestVersion.equals(localVersion))
            return false;

        return new Version(latestVersion).isBiggerThan(new Version(localVersion));
    }

    @Override
    public String getCurrentVersion() {
        return main.getDescription().getVersion();
    }

    @Override
    public String getCachedUpdateVersion() {
        return updateRepository.getLatestVersion();
    }
}
