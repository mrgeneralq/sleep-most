package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.flags.types.EnumFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
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
    public boolean isAfkFlagUsable()
    {
        return PLACEHOLDER_API_ENABLED && ESSENTIALS_ENABLED;
    }

    @Override
    public List<String> getValuesSuggestions(ISleepFlag<?> flag)
    {
        if(flag instanceof EnumFlag)
        {
            EnumFlag<?> enumFlag = (EnumFlag<?>) flag;

            return Arrays.stream(enumFlag.getEnumClass().getEnumConstants())
                    .map(enumInstance -> getValueDisplayName(enumFlag, enumInstance))
                    .collect(toList());
        }
        return Collections.emptyList();
    }


    @SuppressWarnings("unchecked")
    @Override
    public <V> String getValueDisplayName(ISleepFlag<V> flag, Object value)
    {
        return flag.getDisplayName((V) value);
    }

    @Override
    public <V, R> R flagAction(ISleepFlag<V> flag, Function<ISleepFlag<V>, R> function)
    {
        return function.apply(flag);
    }

    @Override
    public Map<World, Map<ISleepFlag<?>, Object>> getWorldsWithIllegalValues()
    {
        return this.configService.getEnabledWorlds().stream()
                .map(world -> new AbstractMap.SimpleEntry<>(world, findIllegalValuesAt(world)))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<ISleepFlag<?>, Object> findIllegalValuesAt(World world)
    {
        return this.flagsRepository.getFlags().stream()
                .map(flag -> new AbstractMap.SimpleEntry<>(flag, configRepository.getFlagValue(flag, world)))
                .filter(entry -> !entry.getKey().isValidValue(entry.getValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
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
}
