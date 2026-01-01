package me.mrgeneralq.sleepmost.core.interfaces;

public interface IUpdateService {

    boolean hasUpdate();
    boolean hasUpdate(String localVersion);

    String getCurrentVersion();
    String getCachedUpdateVersion();
}
