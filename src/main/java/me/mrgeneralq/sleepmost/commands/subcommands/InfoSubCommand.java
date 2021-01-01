package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageTemplate;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.ChatColorUtils;
import me.mrgeneralq.sleepmost.statics.SleepFlagMapper;
import me.mrgeneralq.sleepmost.statics.Message;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class InfoSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final ISleepFlagService sleepFlagService;
    private final SleepFlagMapper sleepFlagMapper;

    public InfoSubCommand(ISleepService sleepService, IMessageService messageService, ISleepFlagService sleepFlagService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.sleepFlagService = sleepFlagService;
        this.sleepFlagMapper = SleepFlagMapper.getMapper();
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {


        if(!(sender instanceof Player)){

            String onlyPlayersCommandMessage = messageService.getNewBuilder(MessageTemplate.ONLY_PLAYERS_COMMAND)
                    .usePrefix(true)
                    .build();

            sender.sendMessage(onlyPlayersCommandMessage);
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if(!sleepService.enabledForWorld(world)){

            String disabledForWorldMessage = messageService.getNewBuilder(MessageTemplate.CURRENTLY_DISABLED)
                    .setWorld(world)
                    .setPlayer(player)
                    .build();

            player.sendMessage(disabledForWorldMessage);
            return true;
        }

        List<String> flags = sleepFlagMapper.getAllFlags();

        List<ISleepFlag<?>> sleepFlagCollection = new ArrayList<>();

        for(String flag : flags){

            if(sleepFlagService.getSleepFlag(flag).getValue(world) == null)
                continue;

            sleepFlagCollection.add(sleepFlagService.getSleepFlag(flag));
        }


        sender.sendMessage(ChatColorUtils.colorize("&b*********************************************"));
        sender.sendMessage(ChatColorUtils.colorize(String.format("&e&l  FLAGS &o&7world: &c&l%s", world.getName())));
        sender.sendMessage(ChatColorUtils.colorize(String.format("&e&l  FLAGS &o&7world: &c&l%s", world.getName())));
        sender.sendMessage(ChatColorUtils.colorize("&b*********************************************"));
        sender.sendMessage(ChatColorUtils.colorize(""));

        for(ISleepFlag<?> flagItem : sleepFlagCollection)
            sender.sendMessage(ChatColorUtils.colorize(String.format("&e%s &b%s",flagItem.getFlagName(), flagItem.getValue(world))));

        sender.sendMessage(ChatColorUtils.colorize(""));
        sender.sendMessage(ChatColorUtils.colorize("&b*********************************************"));
        return true;
    }
}
