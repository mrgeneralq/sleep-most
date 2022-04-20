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
        this.messages.put(ConfigMessage.BOSS_BAR_TITLE, new Message("bossbar.title", "This is the default title"));
        this.messages.put(ConfigMessage.NO_CONSOLE_COMMAND, new Message("command.no-console-command", "here is a default no console command"));
        this.messages.put(ConfigMessage.NIGHT_SKIPPED, new Message("night.night-skipped", "night skipped"));
        this.messages.put(ConfigMessage.PREFIX, new Message("prefix", "[sleep-most]"));

    }

    public Message getMessage(ConfigMessage message){
        return this.messages.get(message);
    }

    public List<Message> getAllMessages(){
        return new ArrayList<>(this.messages.values());
    }
}
