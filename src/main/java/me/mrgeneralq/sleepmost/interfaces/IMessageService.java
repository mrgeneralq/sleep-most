package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.models.Message;
import me.mrgeneralq.sleepmost.templates.MessageTemplate;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;

import java.util.List;

public interface IMessageService 
{
	String getConfigMessage(ConfigMessage message);
	ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause);
	String getPlayersLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount);
	void sendMessage(CommandSender sender, String message);
	void sendWorldMessage(World world, String message);
	void sendOPMessage(String message);
	void sendPlayerLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount);
	void sendNightSkippedMessage(World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause cause);
	MessageBuilder newBuilder(String rawMessage);
	MessageBuilder newBuilder(MessageTemplate messageTemplate);
	MessageBuilder newPrefixedBuilder(String rawMessage);
	String fromTemplate(MessageTemplate messageTemplate);
    MessageBuilder getMessageFromConfig(ConfigMessage configMessage);
    List<Message> getMessages();
    boolean messagePathExists(String path);
	void createMessage(Message message);
	void createMissingMessages();


}
