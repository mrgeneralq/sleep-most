package me.qintinator.sleepmost.statics;

import java.util.HashMap;

import org.bukkit.ChatColor;

import me.qintinator.sleepmost.Main;

public class ConfigMessageMapper {
	
	public enum ConfigMessage {PREFIX, NIGHT_SKIPPED, PLAYERS_LEFT_TO_SKIP_NIGHT}
	private final Main main;
	HashMap<ConfigMessage, String> configMessages = new HashMap<>();	
	
	
	
	public ConfigMessageMapper(Main main) {
		this.main = main;
			
		configMessages.put(ConfigMessage.PREFIX, "messages.prefix");
		configMessages.put(ConfigMessage.NIGHT_SKIPPED, "messages.night-skipped");
		configMessages.put(ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT, "messages.players-left");
		
	}
	
	
	

	
	public String getMessage(ConfigMessage configMessage, Boolean includePrefix) {
		
		if(includePrefix)
		return ChatColor.translateAlternateColorCodes('&', getMessage(ConfigMessage.PREFIX, false)+ " " + main.getConfig().getString(configMessages.get(configMessage)));
		return ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(configMessages.get(configMessage)));
	}
	
	

}
