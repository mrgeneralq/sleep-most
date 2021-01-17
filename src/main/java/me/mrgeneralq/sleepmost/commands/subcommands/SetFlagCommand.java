package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetFlagCommand implements ISubCommand, TabCompleter {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final IFlagService sleepFlagService;
    private final IFlagsRepository flagsRepository;

    public SetFlagCommand(ISleepService sleepService, IMessageService messageService, IFlagService sleepFlagService, IFlagsRepository flagsRepository) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.sleepFlagService = sleepFlagService;
        this.flagsRepository = flagsRepository;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.NO_CONSOLE_COMMAND));
            return true;
        }

        World world = CommandSenderUtils.getWorldOf(sender);

        if(!sleepService.enabledForWorld(world)){
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.CURRENTLY_DISABLED));
            return true;
        }

        if(args.length < 2) {
            sender.sendMessage(messageService.newPrefixedBuilder("&btype &e/sleepmost setflag <flag> <value>").build());
            return true;
        }
        String flagName = args[1];

        if(!flagsRepository.flagExists(flagName)) {
            sender.sendMessage(messageService.newPrefixedBuilder("&cThis flag does not exist!").build());
            sender.sendMessage(messageService.newBuilder("&bPossible flags are: &e%flagsNames")
                    .setPlaceHolder("%flagsNames", StringUtils.join(flagsRepository.getFlagsNames(), ", "))
                    .build());
            return true;
        }
        ISleepFlag<?> sleepFlag = flagsRepository.getFlag(flagName);

        if(args.length < 3){
            sender.sendMessage(messageService.newPrefixedBuilder("&cMissing value! Use &e%usageCommand")
                    .setPlaceHolder("%usageCommand", getUsageCommand(sleepFlag))
                    .build());
            return true;
        }
        String stringValue = args[2];

        if(!sleepFlag.isValidValue(stringValue)) {
            sender.sendMessage(messageService.newPrefixedBuilder("&cInvalid format! Use &e%usageCommand")
                    .setPlaceHolder("%usageCommand", getUsageCommand(sleepFlag))
                    .build());
            return true;
        }
        this.sleepFlagService.setStringValueAt(sleepFlag, world, stringValue);

        sender.sendMessage(messageService.newPrefixedBuilder("&bFlag &c%flag &bis now set to &e%value &bfor world &e%world")
                .setPlaceHolder("%flag", sleepFlag.getName())
                .setPlaceHolder("%value", stringValue)
                .setPlaceHolder("%world", world.getName())
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

    private String getUsageCommand(ISleepFlag<?> flag)
    {
        return String.format("/sleepmost setflag %s %s", flag.getName(), flag.getValueDescription());
    }
}