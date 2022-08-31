package me.mrgeneralq.sleepmost.managers;

import org.bukkit.Bukkit;

public class HookManager {

    public static boolean isGSitInstalled(){
        return Bukkit.getPluginManager().getPlugin("GSit") != null;
    }



}
