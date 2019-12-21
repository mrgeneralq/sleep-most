package me.qintinator.sleepmost.commands.subcommands;

import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.interfaces.ISleepFlag;
import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.interfaces.ISubCommand;
import me.qintinator.sleepmost.statics.Message;
import me.qintinator.sleepmost.statics.SleepFlagMapper;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class SetFlagCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final SleepFlagMapper flagMapper;

    public SetFlagCommand(ISleepService sleepService) {
        this.sleepService = sleepService;
        this.flagMapper = SleepFlagMapper.getMapper();
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Message.commandOnlyForPlayers);
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if(!sleepService.enabledForWorld(world)){
            player.sendMessage(Message.currentlyDisabled);
            return true;
        }

        if(args.length < 2){
            player.sendMessage(Message.getMessage("&btype &e/sleepmost setflag <flag> <value>"));
            return true;
        }

        String flag = args[1];

        if(!flagMapper.flagExists(flag)){
            player.sendMessage(Message.getMessage("&cThis flag does not exist!"));
            player.sendMessage(Message.getMessage("&bPossible flags are: &e " + flagMapper.getAllFlags().stream().collect(Collectors.joining(", "))));
            return true;
        }

        ISleepFlag sleepFlag = flagMapper.getFlag(flag);

        if(args.length < 3){
            player.sendMessage(Message.getMessage("&cMissing value! Use &e" + sleepFlag.getFlagUsage()));
            return true;
        }

        String flagValue = args[2];

        if(!sleepFlag.isValidValue(flagValue)){
            player.sendMessage(Message.getMessage("&cInvalid format! Use &e" + sleepFlag.getFlagUsage()));
            return true;
        }


        sleepService.setFlag(world, sleepFlag, flagValue);
        player.sendMessage(Message.getMessage(String.format("&bFlag &c%s &bis now set to &e%s &bfor world &e%s", sleepFlag.getFlagName(), flagValue, world.getName())));
        return true;
    }
}
