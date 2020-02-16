package me.qintinator.sleepmost.commands;

import me.qintinator.sleepmost.commands.subcommands.DisableSubCommand;
import me.qintinator.sleepmost.commands.subcommands.EnableSubCommand;
import me.qintinator.sleepmost.commands.subcommands.ReloadSubCommand;
import me.qintinator.sleepmost.commands.subcommands.SetFlagCommand;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.interfaces.ISubCommand;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class SleepmostCommand implements CommandExecutor {


    private final HashMap<String, ISubCommand> subCommands = new HashMap<>();
    private final ISleepService sleepService;
    private final IMessageService messageService;

    public SleepmostCommand(ISleepService sleepService, IMessageService messageService){
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.registerSubCommands();
    }

    private void registerSubCommands(){
        subCommands.put("reload", new ReloadSubCommand(this.sleepService, this.messageService));
        subCommands.put("enable", new EnableSubCommand(this.sleepService,this.messageService));
        subCommands.put("disable", new DisableSubCommand(this.sleepService, this.messageService));
        subCommands.put("setflag", new SetFlagCommand(this.sleepService, this.messageService));
    }




    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if(args.length == 0){

            if(!sender.hasPermission("sleepmost.help")){
                messageService.sendMessage(sender, Message.noPermission, false);
                return true;
            }

            messageService.sendMessage(sender,"&b*********************************************",false);
            messageService.sendMessage(sender,"&b*&e  SLEEPMOST &o&7author: MrGeneralQ  &b*",false);
            messageService.sendMessage(sender,"&b*********************************************",false);

            messageService.sendMessage(sender,"", false);
            messageService.sendMessage(sender,"&e/sm &fshow a list of available commands", false);
            messageService.sendMessage(sender,"&e/sm enable &fenable sleepmost in the current world", false);
            messageService.sendMessage(sender,"&e/sm disable &fdisable sleepmost in the current world", false);
            messageService.sendMessage(sender,"&e/sm setflag <flagname> <flagvalue> &fset a flag for the current world",false);
            messageService.sendMessage(sender,"&e/sm reload &freload the config file",false);
            return true;
        }


        String subCommand = args[0];

        if(!sender.hasPermission("sleepmost." + subCommand))
        {
            // check if player has permission of command
            messageService.sendMessage(sender, Message.noPermission, false);
            return true;
        }

       return subCommands.getOrDefault(subCommand, new ErrorCommand(messageService)).executeCommand(sender,command,commandLabel, args);
    }
}
