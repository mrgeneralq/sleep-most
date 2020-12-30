package me.mrgeneralq.sleepmost.builders;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class MessageBuilder {

    private String message= "";
    private boolean usePrefix = false;


    public String build(){

        String messageFormat =(usePrefix)? "%s %s": "%s";
        String output = String.format(messageFormat, this.message, "");

        return output.trim();
    }

    public MessageBuilder setPlayer(Player player){
        this.message = this.message.replaceAll("%player%", player.getName())
                .replaceAll("%dplayer%", player.getDisplayName());
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

    public MessageBuilder initialize(String message){
        this.message = message;
        this.usePrefix = false;
        return this;
    }

    public MessageBuilder setMessage(String message){
        this.message = message;
        return this;
    }

}