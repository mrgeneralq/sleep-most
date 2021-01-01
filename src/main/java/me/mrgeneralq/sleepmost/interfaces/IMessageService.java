package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.enums.MessageTemplate;
import me.mrgeneralq.sleepmost.statics.Message;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;

public interface IMessageService 
{

	String getConfigMessage(ConfigMessage message);
	void sendMessageToWorld(ConfigMessage message, World world);
	ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause);
	String getPlayersLeftMessage(Player player, SleepSkipCause cause);
	void sendMessageToWorld(World world, String string);
	void sendPlayerLeftMessage(Player player, SleepSkipCause cause);
	void sendNightSkippedMessage(World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause cause);
	MessageBuilder getNewBuilder(String rawMessage);
	MessageBuilder getNewBuilder(MessageTemplate messageTemplate);
	String getFromTemplate(MessageTemplate messageTemplate);

}
