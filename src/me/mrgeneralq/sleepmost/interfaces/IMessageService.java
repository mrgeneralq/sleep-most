package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface IMessageService {

public String getMessage(ConfigMessage message, boolean includePrefix);
public String getMessage(String message ,boolean includePrefix);
public void sendMessageToWorld(ConfigMessage message, World world);
public ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause);
public String getPlayersLeftMessage(Player player, SleepSkipCause cause);
public void sendMessageToWorld(World world, String string);
public void sendPlayerLeftMessage(Player player, SleepSkipCause cause);
public void sendMessage(CommandSender sender, String message, boolean showPrefix);
}
