package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.templates.MessageTemplate;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public KickSubCommand(ISleepService sleepService, IMessageService messageService) {
    this.sleepService = sleepService;
    this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.NO_CONSOLE_COMMAND));
            return true;
        }

        Player player = (Player) sender;

        World world = CommandSenderUtils.getWorldOf(sender);

        if(args.length < 2){
            this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.SPECIFY_PLAYER));
            return true;
        }

        String targetPlayerName = args[1];

        if(Bukkit.getPlayer(targetPlayerName) == null){
            this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.TARGET_NOT_ONLINE));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        if(!this.sleepService.isPlayerAsleep(targetPlayer)){
            this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.TARGET_NOT_SLEEPING));
            return true;
        }

        targetPlayer.teleport(targetPlayer.getLocation());
        this.messageService.sendMessage(sender,"Player has been kicked from the bed");
        this.sleepService.setSleeping(targetPlayer, false);
        return true;
    }
}
