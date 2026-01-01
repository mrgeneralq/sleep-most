package me.mrgeneralq.sleepmost.core.flags.types;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.serialization.BooleanSerialization;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BooleanFlag extends TabCompletedFlag<Boolean> {

    public BooleanFlag(String name, String valueDescription, AbstractFlagController<Boolean> controller, boolean defaultValue) {
        super(name, valueDescription, controller, BooleanSerialization.INSTANCE, defaultValue);
    }

    public BooleanFlag(String name, AbstractFlagController<Boolean> controller, boolean defaultValue) {
        this(name, "<true, false>", controller, defaultValue);
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, List<String> args) {
        return new ArrayList<>(convertToArrayList(new String[]{"true", "false"}))
                .stream().filter(it -> it.contains(args.get(0)) || it.equalsIgnoreCase(args.get(0)))
                .collect(Collectors.toList());
    }
}
