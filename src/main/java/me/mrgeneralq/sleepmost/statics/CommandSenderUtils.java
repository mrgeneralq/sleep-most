package me.mrgeneralq.sleepmost.statics;

import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandSenderUtils {


    public static boolean hasWorld(CommandSender commandSender){
        return !(commandSender instanceof ConsoleCommandSender);
    }

    public static World getWorldOf(CommandSender commandSender){

        if(commandSender instanceof Player)
            return ((Entity) commandSender).getWorld();

        if(commandSender instanceof BlockCommandSender){
           return  ((BlockCommandSender) commandSender).getBlock().getWorld();
        }

        return null;
    }
}
