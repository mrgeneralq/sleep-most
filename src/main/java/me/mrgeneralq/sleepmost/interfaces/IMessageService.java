package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.messages.MessageBuilder;
import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;

public interface IMessageService 
{

	String getConfigMessage(ConfigMessage message);
	void sendMessageToWorld(ConfigMessage message, World world);
	ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause);
	String getPlayersLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount);
	void sendMessageToWorld(World world, String message);
	void sendPlayerLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount);
	void sendNightSkippedMessage(World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause cause);
	MessageBuilder newBuilder(String rawMessage);
	MessageBuilder newBuilder(MessageTemplate messageTemplate);
	MessageBuilder newPrefixedBuilder(String rawMessage);
	String fromTemplate(MessageTemplate messageTemplate);
}
