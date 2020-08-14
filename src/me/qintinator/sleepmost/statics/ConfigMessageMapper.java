package me.qintinator.sleepmost.statics;

import java.util.HashMap;

import me.qintinator.sleepmost.enums.ConfigMessage;
import org.bukkit.ChatColor;

import me.qintinator.sleepmost.Sleepmost;

public class ConfigMessageMapper {


	private static ConfigMessageMapper messageMapper;
	private final HashMap<ConfigMessage, String> configMessages;
	private Sleepmost main;

	private ConfigMessageMapper(){
		configMessages = new HashMap<>();
		configMessages.put(ConfigMessage.PREFIX, "messages.prefix");
		configMessages.put(ConfigMessage.NIGHT_SKIPPED, "messages.night-skipped");
		configMessages.put(ConfigMessage.STORM_SKIPPED, "messages.storm-skipped");
		configMessages.put(ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT, "messages.players-left-night");
		configMessages.put(ConfigMessage.PLAYERS_LEFT_TO_SKIP_STORM, "messages.players-left-storm");
		configMessages.put(ConfigMessage.SLEEP_PREVENTED, "messages.sleep-prevented");
		configMessages.put(ConfigMessage.NO_SLEEP_THUNDERSTORM, "messages.no-sleep-storm");
	}



	public static ConfigMessageMapper getMapper(){
		if(messageMapper == null)
			messageMapper = new ConfigMessageMapper();
			return messageMapper;
	}

	public void initialize(Sleepmost main){
		this.main = main;
	}

	public String
	getMessagePath(ConfigMessage message){
		return configMessages.get(message);
	}


	public String getMessage(ConfigMessage configMessage, Boolean includePrefix) {
		
		if(includePrefix)
		return ChatColor.translateAlternateColorCodes('&', getMessage(ConfigMessage.PREFIX, false)+ " " + main.getConfig().getString(configMessages.get(configMessage)));
		return ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(configMessages.get(configMessage)));
	}
	
	

}
