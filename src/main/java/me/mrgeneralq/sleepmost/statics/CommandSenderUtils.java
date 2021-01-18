package me.mrgeneralq.sleepmost.statics;

import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class CommandSenderUtils {

    public static boolean hasWorld(CommandSender commandSender){
        return getWorldOf(commandSender) != null;
    }

    public static World getWorldOf(CommandSender commandSender){

        if(commandSender instanceof Entity)
            return ((Entity) commandSender).getWorld();

        if(commandSender instanceof BlockCommandSender){
           return ((BlockCommandSender) commandSender).getBlock().getWorld();
        }

        return null;
    }
}
