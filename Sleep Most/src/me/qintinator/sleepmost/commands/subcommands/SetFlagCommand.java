package me.qintinator.sleepmost.commands.subcommands;

import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISleepFlag;
import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.interfaces.ISubCommand;
import me.qintinator.sleepmost.statics.Message;
import me.qintinator.sleepmost.statics.SleepFlagMapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SetFlagCommand implements ISubCommand , TabCompleter {

    private final ISleepService sleepService;
    private final SleepFlagMapper flagMapper;
    private final IMessageService messageService;

    public SetFlagCommand(ISleepService sleepService, IMessageService messageService) {
        this.sleepService = sleepService;
        this.flagMapper = SleepFlagMapper.getMapper();
        this.messageService = messageService;
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

        if(args.length < 2){
            messageService.sendMessage(player, "&btype &e/sleepmost setflag <flag> <value>", true);
            return true;
        }

        String flag = args[1];

        if(!flagMapper.flagExists(flag)){
            messageService.sendMessage(player, "&cThis flag does not exist!", true);

            String flagListStr = "&bPossible flags are: &e " + flagMapper.getAllFlags().stream().collect(Collectors.joining(", "));
            messageService.sendMessage(player, flagListStr, false);

            return true;
        }

        ISleepFlag sleepFlag = flagMapper.getFlag(flag);

        if(args.length < 3){
            messageService.sendMessage(player, "&cMissing value! Use &e" + sleepFlag.getFlagUsage(),true);
            return true;
        }

        String flagValue = args[2];

        if(!sleepFlag.isValidValue(flagValue)){
            messageService.sendMessage(player,"&cInvalid format! Use &e " + sleepFlag.getFlagUsage(), true);
            return true;
        }

        sleepService.setFlag(world, sleepFlag, flagValue);
        String messageFormat = String.format("&bFlag &c%s &bis now set to &e%s &bfor world &e%s", sleepFlag.getFlagName(), flagValue, world.getName());
        messageService.sendMessage(player, messageFormat, true);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {


        List<String> list = new ArrayList<>();
        list.add("aaaa");
        list.add("bbbb");

        //if(args.length == 2)
            return list;

     //   return null;
    }
}
