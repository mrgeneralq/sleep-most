package me.mrgeneralq.sleepmost.flags.types;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.StringSerialization;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public abstract class SoundResourceFlag extends TabCompletedFlag<String> {

    public SoundResourceFlag(String name, String defaultValue, AbstractFlagController<String> controller) {
        super(name, "<sound_resource_location> (example: entity.wither.spawn)", controller, StringSerialization.INSTANCE, defaultValue);
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, List<String> args) {
        if (args.isEmpty()) return new ArrayList<>();

        Bukkit.getLogger().log(Level.INFO, "Currently doing args: " + args);
        List<String> result = Arrays.stream(Sound.values())
                .filter(sound ->
                        sound.getKey().getKey().equalsIgnoreCase(args.get(0))
                     || sound.getKey().getKey().contains(args.get(0))
                )
                .map(Sound::getKey)
                .map(NamespacedKey::getKey)
                .collect(Collectors.toList());

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            // TODO: Figure out how to get the completion results from player resource packs
        }

        return result;
    }
}
