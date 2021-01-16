package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static java.util.Comparator.comparing;
import static me.mrgeneralq.sleepmost.statics.ChatColorUtils.colorize;

public class InfoSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final IFlagService flagService;
    private final IFlagsRepository flagsRepository;

    public InfoSubCommand(ISleepService sleepService, IMessageService messageService, IFlagService flagService, IFlagsRepository flagsRepository) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.flagService = flagService;
        this.flagsRepository = flagsRepository;
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

        if(!sleepService.enabledForWorld(world))
        {
            player.sendMessage(messageService.newBuilder(MessageTemplate.CURRENTLY_DISABLED)
                    .setWorld(world)
                    .setPlayer(player)
                    .build());
            return true;
        }
        sender.sendMessage(colorize("&b*********************************************"));
        sender.sendMessage(colorize(String.format("&e&lFLAGS &o&7world: &c&l%s", world.getName())));
        sender.sendMessage(colorize("&b*********************************************"));

        this.flagsRepository.getFlags().stream()
                .sorted(comparing(ISleepFlag::getName))
                .map(flag -> getValueAtMessage(flag, world))
                .forEach(player::sendMessage);

        sender.sendMessage(colorize("&b*********************************************"));
        return true;
    }
    private String getValueAtMessage(ISleepFlag<?> flag, World world)
    {
        return messageService.newBuilder("&e%flagName &b%value")
                .setPlaceHolder("%flagName", flag.getName())
                .setPlaceHolder("%value", this.flagService.getValueDisplayName(flag, flag.getValueAt(world)))
                .build();

        //this.flagService.flagAction(flag, f -> f.getSerialization().getDisplayName(f.getValueAt(world)))
        //flag.getSerialization().getDisplayName(flag.getValueAt(world))
    }
}
