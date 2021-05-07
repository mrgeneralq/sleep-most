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

    public UpdateRepository(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String getLatestVersion() {

        if (lastChecked == null)
            lastChecked = new Date();

        double difference = (new Date().getTime() - lastChecked.getTime());

        if (difference < 7200000)
            return this.cachedUpdateVersion;

        //send a request to spigot's website for the latest version
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
             Scanner scanner = new Scanner(inputStream)) {

            if (scanner.hasNext())
                this.cachedUpdateVersion = scanner.next();

        } catch (IOException exception) {
            return cachedUpdateVersion;
        }
        return cachedUpdateVersion;
    }
}