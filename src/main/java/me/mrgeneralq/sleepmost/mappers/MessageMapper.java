package me.mrgeneralq.sleepmost.mappers;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.models.Message;

import java.util.HashMap;
import java.util.Map;

public class MessageMapper {

    private static MessageMapper mapper;
    private Map<ConfigMessage, Message> messages = new HashMap<>();

    private MessageMapper() {
        loadMessages();
    }

    public static MessageMapper getMapper(){
        if(mapper == null)
            mapper = new MessageMapper();
        return mapper;
    }


    private void loadMessages(){
        this.messages.put(ConfigMessage.BOSS_BAR_TITLE, new Message("bossbar.title", ""));
    }

    public Message getMessage(ConfigMessage message){

    }
}
