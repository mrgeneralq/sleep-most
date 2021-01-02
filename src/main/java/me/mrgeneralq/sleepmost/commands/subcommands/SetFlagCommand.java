package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageTemplate;
import me.mrgeneralq.sleepmost.mappers.SleepFlagMapper;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.NO_PERMISSION));
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if(!sleepService.enabledForWorld(world)){
            player.sendMessage(messageService.fromTemplate(MessageTemplate.CURRENTLY_DISABLED));
            return true;
        }

        if(args.length < 2) {
            player.sendMessage(messageService.newPrefixedBuilder("&btype &e/sleepmost setflag <flag> <value>").build());
            return true;
        }
        String flag = args[1];

        if(!flagMapper.flagExists(flag)) {
            player.sendMessage(messageService.newPrefixedBuilder("&cThis flag does not exist!").build());
            player.sendMessage(getPossibleFlagsMessage());
            return true;
        }
        ISleepFlag<?> sleepFlag = flagMapper.getFlag(flag);

        if(args.length < 3){
            player.sendMessage(messageService.newPrefixedBuilder("&cMissing value! Use &e" + sleepFlag.getFlagUsage()).build());
            return true;
        }

        String flagValue = args[2];

        if(!sleepFlag.isValidValue(flagValue)) {
            player.sendMessage(messageService.newPrefixedBuilder("&cInvalid format! Use &e " + sleepFlag.getFlagUsage()).build());
            return true;
        }

        sleepService.setFlag(world, sleepFlag, flagValue);

        player.sendMessage(messageService.newPrefixedBuilder("&bFlag &c%flag% &bis now set to &e%value% &bfor world &e%world%")
                .setPlaceHolder("%flag%", sleepFlag.getFlagName())
                .setPlaceHolder("%value%", flagValue)
                .setPlaceHolder("%world%", world.getName())
                .build());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {


        List<String> list = new ArrayList<>();
      //  list.add("aaaa");
      //  list.add("bbbb");

        //if(args.length == 2)
            return list;

     //   return null;
    }
    private String getPossibleFlagsMessage()
    {
        String flagsNames = StringUtils.join(flagMapper.getAllFlags(), ", ");

        return messageService.newBuilder("&bPossible flags are: &e " + flagsNames).build();
    }
}