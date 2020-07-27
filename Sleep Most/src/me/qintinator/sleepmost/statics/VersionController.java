package me.qintinator.sleepmost.statics;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class VersionController {

    private static List<String> oldVersions = new ArrayList();

    public static boolean isOldVersion(){
        oldVersions.add("1.8");
        oldVersions.add("1.9");
        oldVersions.add("1.10");
        oldVersions.add("1.11");
        oldVersions.add("1.12");
        oldVersions.add("1.13");

        for(String oldVersion: oldVersions){
            if(Bukkit.getVersion().contains(oldVersion))
                return true;
        }
            return false;
    }

}
