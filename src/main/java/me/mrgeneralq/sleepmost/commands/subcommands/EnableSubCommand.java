package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnableSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public EnableSubCommand(ISleepService sleepService, IMessageService messageService) {
    this.sleepService = sleepService;
    this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.NO_CONSOLE_COMMAND));
            return true;
        }

        World world = CommandSenderUtils.getWorldOf(sender);

        if(sleepService.enabledForWorld(world)){
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.ALREADY_ENABLED_FOR_WORLD));
           return true;
        }

        sleepService.enableForWorld(world);
        sender.sendMessage(messageService.fromTemplate(MessageTemplate.ENABLED_FOR_WORLD));
        return true;
    }
}
