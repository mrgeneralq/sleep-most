package me.mrgeneralq.sleepmost.statics;

import org.bukkit.ChatColor;

import static me.mrgeneralq.sleepmost.statics.ChatColorUtils.colorize;

public class Message 
{
	public static final String 
	NO_PERMISSION = "&cYou don't have permission to that command!",
	UNKNOWN_COMMAND = "&cUnknown command! Type /sleepmost to get a list of available commands",
	CONFIG_RELOADED = "&bSleepmost config reloaded!",
	ONLY_PLAYERS_COMMAND = "&cThis command can only be executed by a player!",
	ALREADY_ENABLED_FOR_WORLD = "&cSleepmost is already enabled for this world",
	ALREADY_DISABLED_FOR_WORLD = "&cSleepmost is already disabled for this world",
	ENABLED_FOR_WORLD = colorize("&bSleepmost is now &aenabled &bfor this world"),
	DISABLED_FOR_WORLD = colorize("&bSleepmost is now &cdisabled &bfor this world"),
	CURRENTLY_DISABLED = colorize("&bSleepmost is currently &cdisabled &bfor this world. Type &a/sm enable &b to enable sleepmost for this world");
}
