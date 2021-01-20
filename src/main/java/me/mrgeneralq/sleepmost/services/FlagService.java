package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

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

    @Override
    public <V> String getValueDisplayName(ISleepFlag<V> flag, Object value)
    {
        return flag.getDisplayName((V) value);
    }

    private void notifyAboutIllegalValues(World world, Map<ISleepFlag<?>, Object> illegalValues)
    {
        illegalValues.forEach((flag, value) ->
        {
            Bukkit.getConsoleSender().sendMessage(messageService.newPrefixedBuilder("&cThe value of the &e%flagName &cflag at &b%worldName &cis &4illegal(&c%value&4) and was reset.")
                    .setPlaceHolder("%flagName", flag.getName())
                    .setPlaceHolder("%value", value.toString())
                    .setPlaceHolder("%worldName", world.getName())
                    .build());

            setDefaultValueAt(world, flag);
        });
    }
    private Map<World, Map<ISleepFlag<?>, Object>> getWorldsWithIllegalValues()
    {
        return this.configService.getEnabledWorlds().stream()
                .map(world -> new SimpleEntry<>(world, getIllegalValuesAt(world)))
                .filter(entry -> !entry.getValue().isEmpty()) //remove the worlds that have no illegal values
                .collect(toMap(Entry::getKey, Entry::getValue));
    }
    private Map<ISleepFlag<?>, Object> getIllegalValuesAt(World world)
    {
        Map<ISleepFlag<?>, Object> illegalValues = new HashMap<>();

        for(ISleepFlag<?> flag : this.flagsRepository.getFlags()){

            Object configValue = this.configRepository.getFlagValue(flag, world);

            if(configValue == null) {
                System.out.println(String.format("%s flag wasn't found in the config, it was created with its default value.", flag.getName()));
                setDefaultValueAt(world, flag);
                continue;
            }
            if(!flag.isValidValue(configValue))
                illegalValues.put(flag, configValue);
        }
        return illegalValues;

        /*return this.flagsRepository.getFlags().stream()
                .map(flag -> new SimpleEntry<>(flag, this.configRepository.getFlagValue(flag, world)))
                .filter(entry -> !entry.getKey().isValidValue(entry.getValue()))
                .collect(toMap(Entry::getKey, Entry::getValue));*/
    }
    private <V> void setDefaultValueAt(World world, ISleepFlag<V> flag){
        V defaultValue = flag.getDefaultValue();

        flag.setValueAt(world, defaultValue);
    }
}
