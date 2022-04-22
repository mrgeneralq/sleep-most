package me.mrgeneralq.sleepmost.mappers;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.models.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageMapper {

    private static MessageMapper mapper;
    private final Map<ConfigMessage, Message> messages = new HashMap<>();

    private MessageMapper() {}

    public static MessageMapper getMapper(){
        if(mapper == null)
            mapper = new MessageMapper();
        return mapper;
    }

    public void loadMessages(){

        this.messages.put(ConfigMessage.PREFIX, new Message("path", "default message"));
        this.messages.put(ConfigMessage.NIGHT_SKIPPED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.STORM_SKIPPED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT, new Message("path", "default message"));
        this.messages.put(ConfigMessage.PLAYERS_LEFT_TO_SKIP_STORM, new Message("path", "default message"));
        this.messages.put(ConfigMessage.SLEEP_PREVENTED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.NO_SLEEP_THUNDERSTORM, new Message("path", "default message"));
        this.messages.put(ConfigMessage.BOSS_BAR_TITLE, new Message("path", "default message"));
        this.messages.put(ConfigMessage.NO_CONSOLE_COMMAND, new Message("path", "default message"));
        this.messages.put(ConfigMessage.NO_PERMISSION_COMMAND, new Message("path", "default message"));
        this.messages.put(ConfigMessage.UNKNOWN_COMMAND, new Message("path", "default message"));
        this.messages.put(ConfigMessage.CONFIG_RELOADED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.ONLY_PLAYERS_COMMAND, new Message("path", "default message"));
        this.messages.put(ConfigMessage.ALREADY_ENABLED_FOR_WORLD, new Message("path", "default message"));
        this.messages.put(ConfigMessage.ALREADY_DISABLED_FOR_WORLD, new Message("path", "default message"));
        this.messages.put(ConfigMessage.ENABLED_FOR_WORLD, new Message("path", "default message"));
        this.messages.put(ConfigMessage.DISABLED_FOR_WORLD, new Message("path", "default message"));
        this.messages.put(ConfigMessage.NOT_ENABLED_FOR_WORLD, new Message("path", "default message"));
        this.messages.put(ConfigMessage.CURRENTLY_DISABLED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.CANNOT_SLEEP_NOW, new Message("path", "default message"));
        this.messages.put(ConfigMessage.FLAGS_RESET_SUCCESS, new Message("path", "default message"));
        this.messages.put(ConfigMessage.SLEEP_SUCCESS, new Message("path", "default message"));
        this.messages.put(ConfigMessage.NO_LONGER_SLEEPING, new Message("path", "default message"));
        this.messages.put(ConfigMessage.NO_BED_LOCATION_SET, new Message("path", "default message"));
        this.messages.put(ConfigMessage.TELEPORTED_TO_BED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.SLEEP_CMD_DISABLED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.ONE_PLAYER_SLEEP_SET, new Message("path", "default message"));
        this.messages.put(ConfigMessage.SPECIFY_PLAYER, new Message("path", "default message"));
        this.messages.put(ConfigMessage.TARGET_NOT_SLEEPING, new Message("path", "default message"));
        this.messages.put(ConfigMessage.TARGET_NOT_ONLINE, new Message("path", "default message"));
        this.messages.put(ConfigMessage.INSOMNIA_NOT_SLEEPY, new Message("path", "default message"));
        this.messages.put(ConfigMessage.INSOMNIA_ALREADY_ENABLED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.INSOMNIA_ENABLED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.CMD_ONLY_DURING_NIGHT, new Message("path", "default message"));
        this.messages.put(ConfigMessage.KICK_OUT_BED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.KICKED_PLAYER_FROM_BED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.YOU_ARE_KICKED_FROM_BED, new Message("path", "default message"));
        this.messages.put(ConfigMessage.KICKING_NOT_ALLOWED, new Message("path", "default message"));
    }

    public Message getMessage(ConfigMessage message){
        return this.messages.get(message);
    }

    public List<Message> getAllMessages(){
        return new ArrayList<>(this.messages.values());
    }
}
