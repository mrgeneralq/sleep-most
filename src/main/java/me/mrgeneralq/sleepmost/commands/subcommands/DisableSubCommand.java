package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisableSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public DisableSubCommand(ISleepService sleepService, IMessageService messageService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.ONLY_PLAYERS_COMMAND));
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if (!sleepService.enabledForWorld(world)) {
            player.sendMessage(messageService.fromTemplate(MessageTemplate.ALREADY_DISABLED_FOR_WORLD));
            return true;
        }

        sleepService.disableForWorld(world);
        player.sendMessage(messageService.fromTemplate(MessageTemplate.DISABLED_FOR_WORLD));
        return true;
    }
}

