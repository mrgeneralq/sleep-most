package me.qintinator.sleepmost.statics;

import org.bukkit.ChatColor;

public class Message 
{
	public static final String 
	NO_PERMISSION = ChatColor.RED + "You don't have permission to that command!",
	UNKNOWN_COMMAND = ChatColor.RED + "Unknown command! Type /sleepmost to get a list of available commands",
	CONFIG_RELOADED = ChatColor.AQUA + "Sleepmost config reloaded!",
	ONLY_PLAYERS_COMMAND = ChatColor.RED +  "This command can only be executed by a player!",
	ALREADY_ENABLED_FOR_WORLD = ChatColor.RED + "Sleepmost is already enabled for this world",
	ALREADY_DISABLED_FOR_WORLD = ChatColor.RED + "Sleepmost is already disabled for this world",
	ENABLED_FOR_WORLD = colorize("&bSleepmost is now &aenabled &bfor this world"),
	DISABLED_FOR_WORLD = colorize("&bSleepmost is now &cdisabled &bfor this world"),
	CURRENTLY_DISABLED = colorize("&bSleepmost is currently &cdisabled &bfor this world. Type &a/sm enable &b to enable sleepmost for this world");

	public static String colorize(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
