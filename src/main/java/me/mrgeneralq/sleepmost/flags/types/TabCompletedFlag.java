package me.mrgeneralq.sleepmost.flags.types;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.IValueSerialization;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public abstract class TabCompletedFlag<T> extends AbstractFlag<T> {

    public TabCompletedFlag(String name, String valueDescription, AbstractFlagController<T> controller, IValueSerialization<T> serialization, T defaultValue) {
        super(name, valueDescription, controller, serialization, defaultValue);
    }

    public abstract List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, List<String> args);

    protected <A> List<A> convertToArrayList(A[] array) {
        return Arrays.asList(array);
    }
}
