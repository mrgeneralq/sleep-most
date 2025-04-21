package me.mrgeneralq.sleepmost.services;

public interface IUpdateService {

    boolean hasUpdate();
    boolean hasUpdate(String localVersion);

    String getCurrentVersion();
    String getCachedUpdateVersion();
}
