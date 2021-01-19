package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.AbstractMap.SimpleEntry;

import java.util.*;

import static java.util.stream.Collectors.toMap;

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
    public void reportIllegalValues()
    {
        getWorldsWithIllegalValues().forEach(this::notifyAboutIllegalValues);
    }

    @Override
    public <V> void setStringValueAt(ISleepFlag<V> flag, World world, String stringValue)
    {
        V deserializedValue = flag.getSerialization().parseValueFrom(stringValue);

        flag.setValueAt(world, deserializedValue);
    }

    private void notifyAboutIllegalValues(World world, Map<ISleepFlag<?>, Object> illegalValues)
    {
        illegalValues.forEach((flag, value) ->
        {
            Bukkit.getConsoleSender().sendMessage(messageService.newPrefixedBuilder("&cThe value of the &e%flagName &cflag at &b%worldName &cis &4illegal(&c%value&4)")
                    .setPlaceHolder("%flagName", flag.getName())
                    .setPlaceHolder("%value", String.valueOf(value))
                    .setPlaceHolder("%worldName", world.getName())
                    .build());
        });
        this.configRepository.disableForWorld(world);
        Bukkit.getConsoleSender().sendMessage(messageService.newPrefixedBuilder("&4Therefore the world was &cAuto DISABLED.").build());
    }
    private Map<World, Map<ISleepFlag<?>, Object>> getWorldsWithIllegalValues()
    {
        return this.configService.getEnabledWorlds().stream()
                .map(world -> new SimpleEntry<>(world, findIllegalValuesAt(world)))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    private Map<ISleepFlag<?>, Object> findIllegalValuesAt(World world)
    {
        return this.flagsRepository.getFlags().stream()
                .map(flag -> new SimpleEntry<>(flag, configRepository.getFlagValue(flag, world)))
                .filter(entry -> !entry.getKey().isValidValue(entry.getValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
