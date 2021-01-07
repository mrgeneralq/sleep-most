package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.repositories.SleepFlagRepository;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrgeneralq.sleepmost.statics.ChatColorUtils.colorize;

public class InfoSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public InfoSubCommand(ISleepService sleepService, IMessageService messageService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!(sender instanceof Player)){

            sender.sendMessage(messageService.newBuilder(MessageTemplate.ONLY_PLAYERS_COMMAND)
                    .usePrefix(true)
                    .build());
            return true;
        }
        Player player = (Player) sender;
        World world = player.getWorld();

        if(!sleepService.enabledForWorld(world)) {
            player.sendMessage(messageService.newBuilder(MessageTemplate.CURRENTLY_DISABLED)
                    .setWorld(world)
                    .setPlayer(player)
                    .build());
            return true;
        }
        sender.sendMessage(colorize("&b*********************************************"));
        sender.sendMessage(colorize(String.format("&e&lFLAGS &o&7world: &c&l%s", world.getName())));
        sender.sendMessage(colorize("&b*********************************************"));

        for(ISleepFlag<?> flag : SleepFlagRepository.getInstance().getFlags())
        {
            sender.sendMessage(messageService.newBuilder("&e%flagName &b%value")
                    .setPlaceHolder("%flagName", flag.getName())
                    .setPlaceHolder("%value", flag.getController().getValueAt(world).toString())
                    .build());
        }
        sender.sendMessage(colorize(""));
        sender.sendMessage(colorize("&b*********************************************"));
        return true;
    }
}
