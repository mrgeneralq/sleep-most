package me.qintinator.sleepmost.statics;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

public class VersionController {
	
    private static final List<String> OLD_VERSIONS = Arrays.asList("1.8", "1.9", "1.10", "1.11", "1.12", "1.13");

    public static boolean isOldVersion(){
    	return OLD_VERSIONS.stream().anyMatch(Bukkit.getVersion()::contains);
    }

}
