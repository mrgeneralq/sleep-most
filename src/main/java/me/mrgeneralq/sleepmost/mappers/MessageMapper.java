package me.mrgeneralq.sleepmost.mappers;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.models.Message;

import java.util.HashMap;
import java.util.Map;

public class MessageMapper {

    Map<ConfigMessage, Message> messages = new HashMap<ConfigMessage, Message>(){{


        //TODO work in progress by Malin
     put(ConfigMessage.BOSS_BAR_TITLE , new Message("", ""));



    }};

}
