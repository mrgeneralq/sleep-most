package me.qintinator.sleepmost.commands.subcommands;

import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.interfaces.ISubCommand;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnableSubCommand implements ISubCommand {

    private final ISleepService sleepService;

    public EnableSubCommand(ISleepService sleepService) {
    this.sleepService = sleepService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage(Message.commandOnlyForPlayers);
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if(sleepService.enabledForWorld(world)){
            player.sendMessage(Message.alreadyEnabledForWorld);
            return true;
        }

        sleepService.enableForWorld(world);
        player.sendMessage(Message.enabledForWorld);
        return true;
    }
}
