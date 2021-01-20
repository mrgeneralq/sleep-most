package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import java.util.*;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class FlagService implements IFlagService
{
    private final IFlagsRepository flagsRepository;
    private final IConfigRepository configRepository;
    private final IConfigService configService;
    private final IMessageService messageService;

    private static final boolean
            PLACEHOLDER_API_ENABLED = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null,
            ESSENTIALS_ENABLED = Bukkit.getPluginManager().getPlugin("Essentials") != null;

    public FlagService(IFlagsRepository flagsRepository, IConfigRepository configRepository, IConfigService configService, IMessageService messageService)
    {
        this.flagsRepository = flagsRepository;
        this.configRepository = configRepository;
        this.configService = configService;
        this.messageService = messageService;
    }

    @Override
    public boolean isAfkFlagUsable()
    {
        return PLACEHOLDER_API_ENABLED && ESSENTIALS_ENABLED;
    }

    @Override
    public void reportProblematicValues()
    {
        for(World world : this.configService.getEnabledWorlds()) {

            //Log & Create flags at worlds that didn't had them
            scanForMissingValues(world).forEach(flag ->
            {
                setDefaultValueAt(world, flag);

                Bukkit.getConsoleSender().sendMessage(this.messageService.newPrefixedBuilder("&cThe &e%flag% &cflag was missing at &b%world% so it was created with its default value.")
                        .setPlaceHolder("%flag%", flag.getName())
                        .setPlaceHolder("%world%", world.getName())
                        .build());
            });

            //Log & Reset flags with illegal values
            scanForIllegalValues(world).forEach((flag, value) ->
            {
                setDefaultValueAt(world, flag);

                Bukkit.getConsoleSender().sendMessage(this.messageService.newPrefixedBuilder("&cThe value of the &e%flag% &cflag at &b%world% &cwas &4illegal(&c%value%&4) so it got reset.")
                        .setPlaceHolder("%flag%", flag.getName())
                        .setPlaceHolder("%value%", value.toString())
                        .setPlaceHolder("%world%", world.getName())
                        .build());
            });
        }
    }

    @Override
    public <V> void setStringValueAt(ISleepFlag<V> flag, World world, String stringValue)
    {
        V deserializedValue = flag.getSerialization().parseValueFrom(stringValue);

        flag.setValueAt(world, deserializedValue);
    }

    @Override
    public <V> String getValueDisplayName(ISleepFlag<V> flag, Object value)
    {
        return flag.getDisplayName((V) value);
    }

    private Map<ISleepFlag<?>, Object> scanForIllegalValues(World world)
    {
        return this.flagsRepository.getFlags().stream()
                .map(flag -> new SimpleEntry<>(flag, this.configRepository.getFlagValue(flag, world)))
                .filter(entry -> !entry.getKey().isValidValue(entry.getValue()))
                .collect(toMap(Entry::getKey, Entry::getValue));
    }
    private Set<ISleepFlag<?>> scanForMissingValues(World world){
        return this.flagsRepository.getFlags().stream()
                .filter(flag -> this.configRepository.getFlagValue(flag, world) == null)
                .collect(toSet());
    }

    private <V> void setDefaultValueAt(World world, ISleepFlag<V> flag){
        V defaultValue = flag.getDefaultValue();

        flag.setValueAt(world, defaultValue);
    }
}
