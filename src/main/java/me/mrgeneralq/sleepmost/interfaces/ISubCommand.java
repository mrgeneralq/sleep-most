package me.mrgeneralq.sleepmost.interfaces;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ISubCommand {

    boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args);

}
