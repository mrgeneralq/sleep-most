package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageTemplate;
import me.mrgeneralq.sleepmost.statics.SleepFlagMapper;
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
            sender.sendMessage(messageService.getFromTemplate(MessageTemplate.NO_PERMISSION));
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if(!sleepService.enabledForWorld(world)){
            player.sendMessage(messageService.getFromTemplate(MessageTemplate.CURRENTLY_DISABLED));
            return true;
        }

        if(args.length < 2){

            String setFlagMessage = messageService.getNewBuilder("&btype &e/sleepmost setflag <flag> <value>")
                    .usePrefix(true)
                    .build();

            player.sendMessage(setFlagMessage);
            return true;
        }

        String flag = args[1];

        if(!flagMapper.flagExists(flag)){
            String unknownFlagMessage = messageService.getNewBuilder("&cThis flag does not exist!")
                    .usePrefix(true)
                    .build();

            player.sendMessage(unknownFlagMessage);
            player.sendMessage(getPossibleFlagsMessage());
            return true;
        }

        ISleepFlag<?> sleepFlag = flagMapper.getFlag(flag);

        if(args.length < 3){
            String usageMessage = messageService.getNewBuilder("&cMissing value! Use &e" + sleepFlag.getFlagUsage())
                    .usePrefix(true)
                    .build();

            player.sendMessage(usageMessage);
            return true;
        }

        String flagValue = args[2];

        if(!sleepFlag.isValidValue(flagValue)){
            String invalidFormatMessage = messageService.getNewBuilder("&cInvalid format! Use &e " + sleepFlag.getFlagUsage())
                    .usePrefix(true)
                    .build();

            player.sendMessage(invalidFormatMessage);
            return true;
        }

        sleepService.setFlag(world, sleepFlag, flagValue);
        player.sendMessage(getFlagSetMessage(sleepFlag.getFlagName(), flagValue, world.getName()));
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

        return messageService.getNewBuilder("&bPossible flags are: &e " + flagsNames).build();
    }
    private String getFlagSetMessage(String flagName, String flagValue, String worldName)
    {
        String rawMessage = String.format("&bFlag &c%s &bis now set to &e%s &bfor world &e%s", flagName, flagValue, worldName);

        return messageService.getNewBuilder(rawMessage)
                .usePrefix(true)
                .build();
    }

}
