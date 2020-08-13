package me.qintinator.sleepmost.commands.subcommands;

import me.qintinator.sleepmost.interfaces.*;
import me.qintinator.sleepmost.statics.Message;
import me.qintinator.sleepmost.statics.SleepFlagMapper;
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
            messageService.sendMessage(sender, Message.commandOnlyForPlayers, true);
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if(!sleepService.enabledForWorld(world)){
            messageService.sendMessage(player, Message.currentlyDisabled, true);
            return true;
        }

        List<String> flags = sleepFlagMapper.getAllFlags();

        List<ISleepFlag> sleepFlagCollection = new ArrayList<>();

        for(String flag : flags){

            if(sleepFlagService.getSleepFlag(flag).getValue(world) == null)
                continue;

            sleepFlagCollection.add(sleepFlagService.getSleepFlag(flag));
        }




        messageService.sendMessage(sender,"&b*********************************************",false);
        messageService.sendMessage(sender,String.format("&e&l  FLAGS &o&7world: &c&l%s", world.getName()),false);
        messageService.sendMessage(sender,"&b*********************************************",false);

        messageService.sendMessage(sender,"", false);

        for(ISleepFlag flagItem: sleepFlagCollection)
            messageService.sendMessage(sender,String.format("&e%s &b%s",flagItem.getFlagName(), flagItem.getValue(world)), false);

        messageService.sendMessage(sender,"", false);
        messageService.sendMessage(sender,"&b*********************************************",false);

        return true;
    }
}
