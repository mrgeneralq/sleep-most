package me.qintinator.sleepmost.services;

import me.qintinator.sleepmost.Main;
import me.qintinator.sleepmost.interfaces.IUpdateRepository;
import me.qintinator.sleepmost.interfaces.IUpdateService;


public class UpdateService implements IUpdateService {

    private final IUpdateRepository updateRepository;
    private  final Main main;

    public UpdateService(IUpdateRepository updateRepository, Main main){

        this.updateRepository = updateRepository;
        this.main = main;
    }


    @Override
    public boolean hasUpdate() {

        String onlineVersion = updateRepository.getLatestVersion();

        if(onlineVersion == null)
            return false;

        if(main.getDescription().getVersion().equalsIgnoreCase(onlineVersion))
            return false;

        return true;
    }

    @Override
    public String getCurrentVersion() {
        return main.getDescription().getVersion();
    }

}
