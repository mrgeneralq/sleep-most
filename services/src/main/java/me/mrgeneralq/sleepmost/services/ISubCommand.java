package me.mrgeneralq.sleepmost.services;

import org.jetbrains.annotations.NotNull;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public interface ISubCommand {

    boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args);

    default List<String> tabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, List<String> args) {
        return new ArrayList<>();
    }
}
