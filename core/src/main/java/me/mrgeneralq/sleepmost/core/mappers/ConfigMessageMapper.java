package me.mrgeneralq.sleepmost.core.mappers;

import java.util.HashMap;
import java.util.Map;

import me.mrgeneralq.sleepmost.core.Sleepmost;
import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.statics.ChatColorUtils;

public class ConfigMessageMapper {

	private Sleepmost main;
	private final Map<MessageKey, String> configMessages = new HashMap<>();

	private static ConfigMessageMapper messageMapper;

	private ConfigMessageMapper(){

		configMessages.put(MessageKey.PREFIX, "messages.prefix");
		configMessages.put(MessageKey.NIGHT_SKIPPED, "messages.night-skipped");
		configMessages.put(MessageKey.STORM_SKIPPED, "messages.storm-skipped");
		configMessages.put(MessageKey.PLAYERS_LEFT_TO_SKIP_NIGHT, "messages.players-left-night");
		configMessages.put(MessageKey.PLAYERS_LEFT_TO_SKIP_STORM, "messages.players-left-storm");
		configMessages.put(MessageKey.SLEEP_PREVENTED, "messages.sleep-prevented");
		configMessages.put(MessageKey.NO_SLEEP_THUNDERSTORM, "messages.no-sleep-storm");
		configMessages.put(MessageKey.BOSS_BAR_TITLE, "bossbar.title");
	}
	
	public static ConfigMessageMapper getMapper(){
		if(messageMapper == null)
			messageMapper = new ConfigMessageMapper();
		return messageMapper;
	}
	
	public void initialize(Sleepmost main){
		this.main = main;
	}
	
	public String getMessagePath(MessageKey message){
		return configMessages.get(message);
	}
	
	public String getMessage(MessageKey configMessage, boolean includePrefix)
	{
		String message = "";
		
		if(includePrefix) {
			message += getMessage(MessageKey.PREFIX, false) + " ";
		}
		message += main.getConfig().getString(configMessages.get(configMessage));
		
		return ChatColorUtils.colorizeChatColors(message);
	}
}