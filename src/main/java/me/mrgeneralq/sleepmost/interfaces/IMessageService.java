package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.templates.MessageTemplate;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;

public interface IMessageService 
{
	String getConfigMessage(ConfigMessage message);
	void sendWorldMessage(ConfigMessage message, World world);
	ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause);
	String getPlayersLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount);
	void sendMessage(CommandSender sender, String message);
	void sendWorldMessage(World world, String message);

	void sendWorldMessageWithPermission(World world, String permission, String message);

	void sendWorldMessageWithPermission(World world, String permission, String messageWithPermission, String messageWithoutPermission);

	void sendOPMessage(String message);
	void sendPlayerLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount);
	void sendNightSkippedMessage(World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause cause);
	MessageBuilder newBuilder(String rawMessage);
	MessageBuilder newBuilder(MessageTemplate messageTemplate);
	MessageBuilder newPrefixedBuilder(String rawMessage);
	String fromTemplate(MessageTemplate messageTemplate);

    MessageBuilder getMessageFromConfig(ConfigMessage configMessage);
}
