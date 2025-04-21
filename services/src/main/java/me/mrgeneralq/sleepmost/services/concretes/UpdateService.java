package me.mrgeneralq.sleepmost.services.concretes;


import me.mrgeneralq.sleepmost.repositories.IUpdateRepository;
import me.mrgeneralq.sleepmost.services.IConfigService;
import me.mrgeneralq.sleepmost.services.IUpdateService;
import org.bukkit.plugin.Plugin;

public class UpdateService implements IUpdateService {

    private final Plugin plugin;
    private final IConfigService configService;
    private final IUpdateRepository updateRepository;

    public UpdateService(IUpdateRepository updateRepository, Plugin plugin, IConfigService configService) {
        this.plugin = plugin;
        this.configService = configService;
        this.updateRepository = updateRepository;
    }

    @Override
    public boolean hasUpdate() {
        return hasUpdate(plugin.getDescription().getVersion());
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
        return plugin.getDescription().getVersion();
    }

    @Override
    public String getCachedUpdateVersion() {
        return updateRepository.getLatestVersion();
    }
}
