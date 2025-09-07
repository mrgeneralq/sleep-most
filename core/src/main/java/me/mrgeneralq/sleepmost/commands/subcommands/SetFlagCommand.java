package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.models.enums.MessageKey;
import me.mrgeneralq.sleepmost.models.flags.types.TabCompletedFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.models.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SetFlagCommand implements ISubCommand {
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

        if (!CommandSenderUtils.hasWorld(sender)) {
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.NO_CONSOLE_COMMAND).build());
            return true;
        }

        World world = CommandSenderUtils.getWorldOf(sender);

        if (!sleepService.isEnabledAt(world)) {
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.CURRENTLY_DISABLED)
                    .setWorld(world)
                    .build());
            return true;
        }

        if (args.length < 2) {
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed("&btype &e/sleepmost setflag <flag> <value>").build());
            return true;
        }
        String flagName = args[1];

        if (!flagsRepository.flagExists(flagName)) {
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.FLAG_DOES_NOT_EXIST).setFlag(flagName).build());
            this.messageService.sendMessage(sender, messageService.getMessage("&bPossible flags are: &e%flagsNames")
                    .setPlaceHolder("%flagsNames", StringUtils.join(flagsRepository.getFlagsNames(), ", "))
                    .build());
            return true;
        }
        ISleepFlag<?> sleepFlag = flagsRepository.getFlag(flagName);

        if (args.length < 3) {
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed("&cMissing value! Use &e%usageCommand")
                    .setPlaceHolder("%usageCommand", getUsageCommand(sleepFlag))
                    .build());
            return true;
        }
        String stringValue = args[2];

        if (!sleepFlag.isValidValue(stringValue)) {
            this.messageService.sendMessage(sender, messageService.getMessagePrefixed("&cInvalid format! Use &e%usageCommand")
                    .setPlaceHolder("%usageCommand", getUsageCommand(sleepFlag))
                    .build());
            return true;
        }
        this.sleepFlagService.setStringValueAt(sleepFlag, world, stringValue);

        this.messageService.sendMessage(sender, messageService.getMessagePrefixed(MessageKey.FLAG_SET_IN_WORLD)
                .setFlag(flagName)
                .setWorld(world)
                .setPlaceHolder("%value%", stringValue)
                .build());
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, List<String> args) {
        if (args.size() == 1) {
            return this.flagsRepository.getFlagsNames().stream()
                    .filter(flag -> flag.contains(args.get(0)) || flag.equalsIgnoreCase(args.get(0)))
                    .collect(Collectors.toList());
        }

        if (args.size() == 2) {
            ISleepFlag<?> flag = flagsRepository.getFlags().stream()
                    .filter(f -> f.getName().equalsIgnoreCase(args.get(0)))
                    .findFirst().orElse(null);

            if (flag instanceof TabCompletedFlag<?>) {
                return ((TabCompletedFlag<?>) flag).tabComplete(sender, command, alias, args.subList(1, args.size()));
            }
        }

        return new ArrayList<>();
    }

    private String getUsageCommand(ISleepFlag<?> flag) {
        return String.format("/sleepmost setflag %s %s", flag.getName(), flag.getValueDescription());
    }
}