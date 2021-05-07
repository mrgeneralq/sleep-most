package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.interfaces.IUpdateRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

public class UpdateRepository implements IUpdateRepository {

    private final String resourceId;
    private String cachedUpdateVersion;
    private Date lastChecked;

    private static final int CACHE_TIMER_IN_MS = 7200000;

    public UpdateRepository(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String getLatestVersion() {

        if(this.lastChecked != null && !cacheTimeOver())
            return this.cachedUpdateVersion;

        this.lastChecked = new Date();


        //call and cache spigot's api version
        try {
            this.cachedUpdateVersion = requestSpigotVersion();
        }
        catch(IOException e) {
            this.cachedUpdateVersion = "0";
        }
        return this.cachedUpdateVersion;
    }

    private String requestSpigotVersion() throws IOException {
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
             Scanner scanner = new Scanner(inputStream)) {

            if (scanner.hasNext())
                return scanner.next();
        }
        return null;
    }

    private boolean cacheTimeOver()
    {
        double difference = (new Date().getTime() - this.lastChecked.getTime());

        return difference > CACHE_TIMER_IN_MS;
    }
}