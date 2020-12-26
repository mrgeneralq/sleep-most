package me.mrgeneralq.sleepmost.interfaces;

public interface IUpdateService {

    public boolean hasUpdate();
    public String getCurrentVersion();

    String getCachedUpdateVersion();
}
