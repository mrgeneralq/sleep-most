package me.mrgeneralq.sleepmost.interfaces;

public interface IUpdateService {

    boolean hasUpdate();
    boolean hasUpdate(String version);

    String getCurrentVersion();
    String getCachedUpdateVersion();
}
