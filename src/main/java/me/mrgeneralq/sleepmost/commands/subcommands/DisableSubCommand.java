package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.templates.MessageTemplate;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DisableSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public DisableSubCommand(ISleepService sleepService, IMessageService messageService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.NO_CONSOLE_COMMAND));
            return true;
        }


        World world = CommandSenderUtils.getWorldOf(sender);


        if (!sleepService.isEnabledAt(world)) {
            this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.ALREADY_DISABLED_FOR_WORLD));
            return true;
        }

        sleepService.disableAt(world);
        this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.DISABLED_FOR_WORLD));
        return true;
    }
}

