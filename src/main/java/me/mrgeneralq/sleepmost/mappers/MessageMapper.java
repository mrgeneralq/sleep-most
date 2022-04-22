package me.mrgeneralq.sleepmost.mappers;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.models.ConfigMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageMapper {

    private static MessageMapper mapper;
    private final Map<MessageKey, ConfigMessage> messages = new HashMap<>();

    private MessageMapper() {}

    public static MessageMapper getMapper(){
        if(mapper == null)
            mapper = new MessageMapper();
        return mapper;
    }

    public void loadMessages(){

        this.messages.put(MessageKey.PREFIX, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.NIGHT_SKIPPED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.STORM_SKIPPED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.PLAYERS_LEFT_TO_SKIP_NIGHT, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.PLAYERS_LEFT_TO_SKIP_STORM, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.SLEEP_PREVENTED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.NO_SLEEP_THUNDERSTORM, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.BOSS_BAR_TITLE, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.NO_CONSOLE_COMMAND, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.NO_PERMISSION_COMMAND, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.UNKNOWN_COMMAND, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.CONFIG_RELOADED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.ONLY_PLAYERS_COMMAND, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.ALREADY_ENABLED_FOR_WORLD, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.ALREADY_DISABLED_FOR_WORLD, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.ENABLED_FOR_WORLD, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.DISABLED_FOR_WORLD, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.NOT_ENABLED_FOR_WORLD, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.CURRENTLY_DISABLED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.CANNOT_SLEEP_NOW, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.FLAGS_RESET_SUCCESS, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.SLEEP_SUCCESS, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.NO_LONGER_SLEEPING, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.NO_BED_LOCATION_SET, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.TELEPORTED_TO_BED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.SLEEP_CMD_DISABLED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.ONE_PLAYER_SLEEP_SET, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.SPECIFY_PLAYER, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.TARGET_NOT_SLEEPING, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.TARGET_NOT_ONLINE, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.INSOMNIA_NOT_SLEEPY, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.INSOMNIA_ALREADY_ENABLED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.INSOMNIA_ENABLED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.CMD_ONLY_DURING_NIGHT, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.KICK_OUT_BED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.KICKED_PLAYER_FROM_BED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.YOU_ARE_KICKED_FROM_BED, new ConfigMessage("path", "default message"));
        this.messages.put(MessageKey.KICKING_NOT_ALLOWED, new ConfigMessage("path", "default message"));
    }

    public ConfigMessage getMessage(MessageKey message){
        return this.messages.get(message);
    }

    public List<ConfigMessage> getAllMessages(){
        return new ArrayList<>(this.messages.values());
    }
}
