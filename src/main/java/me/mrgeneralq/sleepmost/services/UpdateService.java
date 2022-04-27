package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import me.mrgeneralq.sleepmost.utils.Version;

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

        if(this.main.earlyAccessModeEnabled()){
            return this.main.getEarlyAccessVersion();
        }

        return main.getDescription().getVersion();
    }

    @Override
    public String getCachedUpdateVersion() {
        return updateRepository.getLatestVersion();
    }
}
