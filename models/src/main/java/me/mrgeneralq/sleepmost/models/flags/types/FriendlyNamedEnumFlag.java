package me.mrgeneralq.sleepmost.models.flags.types;

import me.mrgeneralq.sleepmost.models.enums.FriendlyNamed;
import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.serialization.IValueSerialization;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EnumFlag needs to have a FriendlyNamed enum. We use the friendlyName for presentation in game.
 * @param <T> An enum that implements FriendlyNamed interface.
 */
public abstract class FriendlyNamedEnumFlag<T extends FriendlyNamed> extends TabCompletedFlag<T> {
    private final FriendlyNamed[] enumValues;

    public FriendlyNamedEnumFlag(String name, String valueDescription, AbstractFlagController<T> controller, IValueSerialization<T> serialization, FriendlyNamed[] enumValues, T defaultValue) {
        super(name, valueDescription, controller, serialization, defaultValue);
        this.enumValues = enumValues;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, List<String> args) {
        return Arrays.stream(enumValues)
                .map(FriendlyNamed::friendlyName)
                .filter(it -> it.contains(args.get(0)) || it.equalsIgnoreCase(args.get(0)))
                .collect(Collectors.toList());
    }

    @Override
    public String getDisplayName(T value) {
        return value.friendlyName();
    }
}
