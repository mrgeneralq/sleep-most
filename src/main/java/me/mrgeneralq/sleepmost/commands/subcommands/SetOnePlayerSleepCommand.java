package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.flags.CalculationMethodFlag;
import me.mrgeneralq.sleepmost.flags.PlayersRequiredFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetOnePlayerSleepCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final IFlagService flagService;
    private final IFlagsRepository flagsRepository;

    public SetOnePlayerSleepCommand(ISleepService sleepService, IMessageService messageService, IFlagService flagService, IFlagsRepository flagsRepository) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.flagService = flagService;
        this.flagsRepository = flagsRepository;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.NO_CONSOLE_COMMAND));
            return true;
        }

        Player player = (Player) sender;

        World world = CommandSenderUtils.getWorldOf(sender);

        if (!sleepService.isEnabledAt(world)) {
            this.messageService.sendMessage(sender, messageService.fromTemplate(MessageTemplate.NOT_ENABLED_FOR_WORLD));
            return true;
        }

        CalculationMethodFlag calculationMethodFlag = flagsRepository.getCalculationMethodFlag();
        PlayersRequiredFlag playersRequiredFlag = flagsRepository.getPlayersRequiredFlag();

        this.flagService.setStringValueAt(calculationMethodFlag, world, "players");
        this.flagService.setStringValueAt(playersRequiredFlag, world, "1");

        this.messageService.sendMessage(player, this.messageService.fromTemplate(MessageTemplate.ONE_PLAYER_SLEEP_SET));
        return true;
    }
}

