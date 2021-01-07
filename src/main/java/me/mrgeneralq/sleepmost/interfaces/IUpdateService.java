package me.mrgeneralq.sleepmost.interfaces;

public interface IUpdateService {

    boolean hasUpdate();
    String getCurrentVersion();
    String getCachedUpdateVersion();
}
