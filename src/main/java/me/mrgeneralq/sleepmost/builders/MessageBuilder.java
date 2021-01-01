package me.mrgeneralq.sleepmost.builders;

import me.mrgeneralq.sleepmost.enums.MessageTemplate;
import me.mrgeneralq.sleepmost.statics.ChatColorUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MessageBuilder {

    private String message= "";
    private boolean usePrefix = false;
    private final String prefix;

    public MessageBuilder(String message, String prefix) {
        this.message = message;
        this.prefix = prefix;
    }

    public String build(){
        String finalMessage = this.message.trim();

        if(usePrefix)
            finalMessage = String.format("%s %s", this.prefix, finalMessage).trim();

        return ChatColorUtils.colorize(finalMessage);
    }

    public MessageBuilder setPlayer(Player player){
        this.message = this.message.replaceAll("%player%", player.getName())
                .replaceAll("%dplayer%", player.getDisplayName());
        return this;
    }

    public MessageBuilder usePrefix(boolean usePrefix){
        this.usePrefix = usePrefix;
        return this;
    }

    public MessageBuilder setWorld(World world){
        this.message = this.message.replaceAll("%world%", world.getName());
        return this;
    }

    public MessageBuilder setPlaceHolder(String placeHolder, String value){
        this.message = this.message.replaceAll(placeHolder, value);
        return this;
    }
    public MessageBuilder setMessage(String message){
        this.message = message;
        return this;
    }

    public MessageBuilder fromTemplate(MessageTemplate template){

        this.usePrefix = template.usesPrefix();
        this.setMessage(template.getMessage());
        return this;
    }
}