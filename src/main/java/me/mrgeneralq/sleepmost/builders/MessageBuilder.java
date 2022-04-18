package me.mrgeneralq.sleepmost.builders;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.statics.ChatColorUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MessageBuilder {

    private final String prefix;
    private String message;
    private boolean usePrefix = false;

    public MessageBuilder(String message, String prefix) {
        this.message = message;
        this.prefix = prefix;
    }

    public String build(){
        String finalMessage = this.message.trim();

        if(finalMessage.isEmpty()) {
            return "";
        }

        if(usePrefix)
            finalMessage = String.format("%s %s", this.prefix, finalMessage).trim();

        return ChatColorUtils.colorize(finalMessage);
    }

    public MessageBuilder setPlayer(Player player){
        return setPlaceHolder("%player%", player.getName()).setPlaceHolder("%dplayer%", player.getDisplayName());
    }

    public MessageBuilder usePrefix(boolean usePrefix){
        this.usePrefix = usePrefix;
        return this;
    }

    public MessageBuilder setWorld(World world){
        this.message = this.message.replace("%world%", world.getName());
        return this;
    }

    public MessageBuilder setPlaceHolder(String placeHolder, String value){
        this.message = this.message.replace(placeHolder, value);
        return this;
    }
    public MessageBuilder setMessage(String message){
        this.message = message;
        return this;
    }

    public MessageBuilder setSleepingCount(int count){
        this.message = message.replaceAll("%sleeping-count%", String.valueOf(count));
        return this;
    }

    public MessageBuilder setSleepingRequiredCount(int count){
        this.message = message.replaceAll("%sleeping-required-count%", String.valueOf(count));
        return this;
    }

    public MessageBuilder setCause(SleepSkipCause cause){

        if(cause == SleepSkipCause.UNKNOWN)
            return this;

        String causeMessage = (cause == SleepSkipCause.NIGHT_TIME) ? "night" : "storm";
        this.message = message.replaceAll("%skip-cause%", causeMessage);

        return this;
    }
}