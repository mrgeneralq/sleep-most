package me.qintinator.sleepmost.statics;

import org.bukkit.ChatColor;

public class Message {

    public static String noPermission = ChatColor.RED + "You don't have permission to that command!";
    public static String unknownCommand = ChatColor.RED + "Unknown command! Type /sleepmost to get a list of available commands";
    public static String configReloaded = ChatColor.AQUA + "Sleepmost config reloaded!";
    public static String commandOnlyForPlayers = ChatColor.RED +  "This command can only be executed by a player!";
    public static String alreadyEnabledForWorld = ChatColor.RED + "Sleepmost is already enabled for this world";
    public static String alreadyDisabledForWorld = ChatColor.RED + "Sleepmost is already disabled for this world";
    public static String enabledForWorld = getMessage("&bSleepmost is now &aenabled &bfor this world");
    public static String disabledForWorld = getMessage("&bSleepmost is now &cdisabled &bfor this world");

    public static String currentlyDisabled = getMessage("&bSleepmost is currently &cdisabled &bfor this world. Type &a/sm enable &b to enable sleepmost for this world");



    public static String getMessage(String message){
        return ChatColor.translateAlternateColorCodes('&', message);

    }


}

