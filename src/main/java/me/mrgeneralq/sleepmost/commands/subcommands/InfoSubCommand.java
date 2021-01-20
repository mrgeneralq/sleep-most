package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.NO_CONSOLE_COMMAND));
            return true;
        }

        World world = CommandSenderUtils.getWorldOf(sender);

        if(!sleepService.isEnabledAt(world))
        {
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.CURRENTLY_DISABLED));
            return true;
        }
        sender.sendMessage(colorize("&b*********************************************"));
        sender.sendMessage(colorize(String.format("&e&lFLAGS &o&7world: &c&l%s", world.getName())));
        sender.sendMessage(colorize("&b*********************************************"));

        this.flagsRepository.getFlags().stream()
                .sorted(comparing(ISleepFlag::getName))
                .map(flag -> getValueAtMessage(flag, world))
                .forEach(sender::sendMessage);

        sender.sendMessage(colorize("&b*********************************************"));
        return true;
    }

    private String getValueAtMessage(ISleepFlag<?> flag, World world)
    {
        return messageService.newBuilder("&e%flagName% &b%value%")
                .setPlaceHolder("%flagName%", flag.getName())
                .setPlaceHolder("%value%", this.flagService.getValueDisplayName(flag, flag.getValueAt(world)))
                .build();
    }
}
