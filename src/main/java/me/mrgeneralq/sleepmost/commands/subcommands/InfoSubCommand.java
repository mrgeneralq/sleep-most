package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

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

        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender, messageService.getMessage(MessageKey.NO_CONSOLE_COMMAND).build());
            return true;
        }

        World world = CommandSenderUtils.getWorldOf(sender);

        if(!sleepService.isEnabledAt(world))
        {
            this.messageService.sendMessage(sender, messageService.getMessage(MessageKey.CURRENTLY_DISABLED)
                    .setWorld(world)
                    .build());
            return true;
        }
        sender.sendMessage(colorize("&b*********************************************"));
        sender.sendMessage(colorize(String.format("&e&lFLAGS &o&7world: &c&l%s", world.getName())));
        sender.sendMessage(colorize("&b*********************************************"));

        Set<ISleepFlag<?>> flagList = this.flagsRepository.getFlags();

        int entriesPerPage = 10;
        int page = 1;
        int totalPages = (int) Math.ceil( flagList.size() / (double) entriesPerPage);

        try{
            page = Integer.parseInt(args[1]);
        }catch (Exception ex){}

        if(page > totalPages)
            page = totalPages;

        flagList.stream()
                .sorted(comparing(ISleepFlag::getName))
                .map(flag -> getValueAtMessage(flag, world))
                .skip( entriesPerPage * (page - 1))
                .limit(entriesPerPage)
                .forEach(sender::sendMessage);
        sender.sendMessage("");
        sender.sendMessage(colorize(String.format("&b(&c%s&f/&c%s&b)  &7/sm info <page>", page , totalPages)));
        sender.sendMessage(colorize("&b*********************************************"));
        return true;
    }

    private String getValueAtMessage(ISleepFlag<?> flag, World world)
    {
        return messageService.getMessage("&e%flag% &b%value%")
                .setFlag(flag.getName())
                .setPlaceHolder("%value%", this.flagService.getValueDisplayName(flag, flag.getValueAt(world)))
                .build();
    }
}
