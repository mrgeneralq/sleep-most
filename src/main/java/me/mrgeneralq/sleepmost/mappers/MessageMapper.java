package me.mrgeneralq.sleepmost.mappers;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.models.Message;

import java.util.HashMap;
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
        this.messages.put(ConfigMessage.BOSS_BAR_TITLE, new Message("bossbar.title", ""));
    }

    public Message getMessage(ConfigMessage message){
        return this.messages.get(message);
    }
}
