package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.models.ConfigMessage;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;

import java.util.List;
import java.util.TreeSet;

public interface IMessageService 
{
	MessageKey getSleepSkipCauseMessage(SleepSkipCause cause);
	String getPlayersLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount);
	void sendMessage(CommandSender sender, String message);
	void sendWorldMessage(World world, String message);
	void sendOPMessage(String message);
	void sendPlayerLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount);
	void sendNightSkippedMessage(World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause cause);
	void sendNightSkippedMessage(List<OfflinePlayer> offlinePlayers, World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause cause);
    MessageBuilder getMessage(MessageKey configMessage);

	MessageBuilder getMessage(String message);

	MessageBuilder getMessagePrefixed(MessageKey configMessage);
	MessageBuilder getMessagePrefixed(String string);
    List<ConfigMessage> getMessages();
    boolean messagePathExists(String path);
	void createMessage(ConfigMessage message);
	void createMissingMessages();


    void reloadConfig();
}
