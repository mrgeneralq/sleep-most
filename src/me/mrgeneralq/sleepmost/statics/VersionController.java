package me.mrgeneralq.sleepmost.statics;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

public class VersionController {
	
    public static final String UPDATE_URL = "https://www.spigotmc.org/resources/sleep-most-1-8-1-16-1-configurable-messages-and-percentage.60623/";
    
    private static final List<String> OLD_VERSIONS = Arrays.asList("1.8", "1.9", "1.10", "1.11", "1.12", "1.13");
    
    public static boolean isOldVersion(){
    	return OLD_VERSIONS.stream().anyMatch(Bukkit.getVersion()::contains);
    }
}
