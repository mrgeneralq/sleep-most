package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;

public interface IMessageService 
{
	String getMessage(ConfigMessage message, boolean includePrefix);
	String getMessage(String message ,boolean includePrefix);
	void sendMessageToWorld(ConfigMessage message, World world);
	ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause);
	String getPlayersLeftMessage(Player player, SleepSkipCause cause);
	void sendMessageToWorld(World world, String string);
	void sendPlayerLeftMessage(Player player, SleepSkipCause cause);
	void sendNightSkippedMessage(World world, String lastSleeperName, SleepSkipCause cause);
	MessageBuilder getNewBuilder(String rawMessage);
}
