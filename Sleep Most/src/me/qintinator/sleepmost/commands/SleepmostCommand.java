package me.qintinator.sleepmost.commands;

import me.qintinator.sleepmost.commands.subcommands.DisableSubCommand;
import me.qintinator.sleepmost.commands.subcommands.EnableSubCommand;
import me.qintinator.sleepmost.commands.subcommands.ReloadSubCommand;
import me.qintinator.sleepmost.commands.subcommands.SetFlagCommand;
import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.interfaces.ISubCommand;
import me.qintinator.sleepmost.statics.Message;
import net.minecraft.server.v1_14_R1.ChatMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class SleepmostCommand implements CommandExecutor {


    private final HashMap<String, ISubCommand> subCommands = new HashMap<>();
    private final ISleepService sleepService;

    public SleepmostCommand(ISleepService sleepService){
        this.sleepService = sleepService;
        this.registerSubCommands();
    }

    private void registerSubCommands(){
        subCommands.put("reload", new ReloadSubCommand(this.sleepService));
        subCommands.put("enable", new EnableSubCommand(this.sleepService));
        subCommands.put("disable", new DisableSubCommand(this.sleepService));
        subCommands.put("setflag", new SetFlagCommand(this.sleepService));
    }




    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if(args.length == 0){

            if(!sender.hasPermission("sleepmost.help")){
                sender.sendMessage(Message.noPermission);
                return true;
            }

            sender.sendMessage(Message.getMessage("&b*********************************************"));
            sender.sendMessage(Message.getMessage("&b*&e  SLEEPMOST &o&7author: MrGeneralQ  &b*"));
            sender.sendMessage(Message.getMessage("&b*********************************************"));

            sender.sendMessage("");
            sender.sendMessage(Message.getMessage("&e/sm &fshow a list of available commands"));
            sender.sendMessage(Message.getMessage("&e/sm enable &fenable sleepmost in the current world"));
            sender.sendMessage(Message.getMessage("&e/sm disable &fdisable sleepmost in the current world"));
            sender.sendMessage(Message.getMessage("&e/sm setflag <flagname> <flagvalue> &fset a flag for the current world"));
            sender.sendMessage(Message.getMessage("&e/sm reload &freload the config file"));
            return true;
        }


        String subCommand = args[0];

        if(!sender.hasPermission("sleepmost." + subCommand))
        {
            // check if player has permission of command
            sender.sendMessage(Message.noPermission);
            return true;
        }

       return subCommands.getOrDefault(subCommand, new ErrorCommand()).executeCommand(sender,command,commandLabel, args);
    }
}
