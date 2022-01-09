package me.mrgeneralq.sleepmost.flags.types;

import me.mrgeneralq.sleepmost.enums.FlagEnum;
import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.IValueSerialization;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EnumFlag<T extends Enum<?>> extends TabCompletedFlag<T> {
    private final FlagEnum[] enumValues;

    public EnumFlag(String name, String valueDescription, AbstractFlagController<T> controller, IValueSerialization<T> serialization, FlagEnum[] enumValues, T defaultValue) {
        super(name, valueDescription, controller, serialization, defaultValue);
        this.enumValues = enumValues;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, List<String> args) {
        return Arrays.stream(enumValues)
                .map(FlagEnum::friendlyName)
                .filter(it -> it.contains(args.get(0)) || it.equalsIgnoreCase(args.get(0)))
                .collect(Collectors.toList());
    }
}
