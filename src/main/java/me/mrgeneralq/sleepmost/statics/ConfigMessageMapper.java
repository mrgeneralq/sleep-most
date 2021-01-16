package me.mrgeneralq.sleepmost.statics;

import java.util.HashMap;
import java.util.Map;

import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.enums.ConfigMessage;

public class ConfigMessageMapper {

	private Sleepmost main;
	private final Map<ConfigMessage, String> configMessages = new HashMap<>();

	private static ConfigMessageMapper messageMapper;

	private ConfigMessageMapper(){

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
	
	public String getMessagePath(ConfigMessage message){
		return configMessages.get(message);
	}
	
	public String getMessage(ConfigMessage configMessage, boolean includePrefix) 
	{
		String message = "";
		
		if(includePrefix) {
			message += getMessage(ConfigMessage.PREFIX, false) + " ";
		}
		message += main.getConfig().getString(configMessages.get(configMessage));
		
		return ChatColorUtils.colorizeChatColors(message);
	}
}