package me.mrgeneralq.sleepmost.builders;

import me.mrgeneralq.sleepmost.models.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.statics.ChatColorUtils;
import me.mrgeneralq.sleepmost.utils.TimeUtils;
import org.bukkit.ChatColor;
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
            finalMessage = String.format("%s %s", this.prefix.trim(), finalMessage).trim();


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

    public MessageBuilder setFlag(String flagName){
        this.message = message.replaceAll("%flag%", flagName);
        return this;
    }

    public MessageBuilder setPlayer(String playerName){
        this.message = message.replaceAll("%player%", playerName);
        return this;
    }

    public MessageBuilder setEnabledStatus(boolean  enabledStatus){
        String status = (enabledStatus) ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";
        this.message = message.replaceAll("%status%", status);
        return this;
    }

    public MessageBuilder setTime(int time){
        this.message = message.replaceAll("%time%", TimeUtils.getTimeStringByTicks(time));
        return this;
    }
}