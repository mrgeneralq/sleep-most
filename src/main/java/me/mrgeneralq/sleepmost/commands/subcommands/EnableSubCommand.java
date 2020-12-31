package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.ChatColorUtils;
import me.mrgeneralq.sleepmost.statics.Message;
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

        if(!(sender instanceof Player))
        {
            sender.sendMessage(ChatColorUtils.colorize(Message.ONLY_PLAYERS_COMMAND));
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if(sleepService.enabledForWorld(world)){
           player.sendMessage(ChatColorUtils.colorize(Message.ALREADY_ENABLED_FOR_WORLD));
           return true;
        }

        sleepService.enableForWorld(world);
        player.sendMessage(ChatColorUtils.colorize(Message.ENABLED_FOR_WORLD));
        return true;
    }
}
