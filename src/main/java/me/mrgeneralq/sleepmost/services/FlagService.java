package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import java.util.*;

import static java.util.stream.Collectors.*;

public class FlagService implements IFlagService {
    private final IFlagsRepository flagsRepository;
    private final IConfigRepository configRepository;
    private final IConfigService configService;
    private final IMessageService messageService;

    private static final boolean
            PLACEHOLDER_API_ENABLED = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null,
            ESSENTIALS_ENABLED = Bukkit.getPluginManager().getPlugin("Essentials") != null;

    public FlagService(IFlagsRepository flagsRepository, IConfigRepository configRepository, IConfigService configService, IMessageService messageService) {
        this.flagsRepository = flagsRepository;
        this.configRepository = configRepository;
        this.configService = configService;
        this.messageService = messageService;
    }

    @Override
    public boolean isAfkFlagUsable() {
        return PLACEHOLDER_API_ENABLED && ESSENTIALS_ENABLED;
    }

    @Override
    public void handleProblematicFlags() {
        for (World world : this.configService.getEnabledWorlds()) {
            handleMissingFlags(world);
            handleIllegalValues(world);
        }
    }

    @Override
    public void resetFlagsAt(World world) {
        this.flagsRepository.getFlags().forEach(flag -> resetFlagAt(world, flag));
    }
    
    @Override
    public <V> void resetFlagAt(World world, ISleepFlag<V> flag) {
    	flag.setValueAt(world, flag.getDefaultValue());
    }

    @Override
    public <V> void setStringValueAt(ISleepFlag<V> flag, World world, String stringValue) {
        flag.setValueAt(world, flag.getSerialization().parseValueFrom(stringValue));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> String getValueDisplayName(ISleepFlag<V> flag, Object value) {
        return flag.getDisplayName((V) value);
    }

    /*
    Values Handlers
     */

    private void handleMissingFlags(World world) {

        Set<ISleepFlag<?>> missingFlags = scanForMissingValues(world);

        if (missingFlags.isEmpty())
            return;

        //Log the missing flags
        this.messageService.sendOPMessage(this.messageService.getMessagePrefixed("&f%flagsAmount% &cMissing Flags at &l&4&l%world% &cwere detected and created in the %storageName%: %flagsNames%")
                .setPlaceHolder("%flagsAmount%", Integer.toString(missingFlags.size()))
                .setPlaceHolder("%world%", world.getName())
                .setPlaceHolder("%storageName%", "config")
                .setPlaceHolder("%flagsNames%", getMissingFlagsNames(missingFlags))
                .build());

        //Create & Defaultize the missing flags
        missingFlags.forEach(flag -> resetFlagAt(world, flag));
    }

    private void handleIllegalValues(World world) {

        Map<ISleepFlag<?>, Object> illegalValues = scanForIllegalValues(world);

        if (illegalValues.isEmpty())
            return;

        //Log the flags with illegal values
        this.messageService.sendOPMessage(this.messageService.getMessagePrefixed("&f%flagsAmount% &cFlags at &l&4&l%world% &cwere detected having &4&lIllegal Values &cin the %storageName%, so they got reset:")
                .setPlaceHolder("%flagsAmount%", Integer.toString(illegalValues.size()))
                .setPlaceHolder("%world%", world.getName())
                .setPlaceHolder("%storageName%", "config")
                .build());
        illegalValues.forEach((flag, value) -> this.messageService.sendOPMessage(createIllegalValueMessage(flag.getName(), value)));

        //Defaultize the flags
        illegalValues.keySet().forEach(flag -> resetFlagAt(world, flag));
    }

    /*
    Flags Scans
     */

    private Set<ISleepFlag<?>> scanForMissingValues(World world) {
        return this.flagsRepository.getFlags().stream()
                .filter(flag -> this.configRepository.getFlagValue(flag, world) == null) //take the flags without a value in the config
                .collect(toSet());
    }

    private Map<ISleepFlag<?>, Object> scanForIllegalValues(World world) {
        return this.flagsRepository.getFlags().stream()
                .map(flag -> new SimpleEntry<>(flag, this.configRepository.getFlagValue(flag, world))) //map the flag to itself & its value at the given world
                .filter(entry -> !entry.getKey().isValidValue(entry.getValue())) //remove the flags with a valid value
                .collect(toMap(Entry::getKey, Entry::getValue));
    }

    /*
    Flags Messages
     */

    private String getMissingFlagsNames(Collection<ISleepFlag<?>> missingFlags) {
        return missingFlags.stream()
                .map(flag -> ChatColor.GREEN + flag.getName())
                .collect(joining("&f, ", "", "&f."));
    }

    private String createIllegalValueMessage(String flagName, Object value) {
        return this.messageService.getMessagePrefixed("&b> &a%flag% &f(was &c%illegalValue%f&f)")
                .setPlaceHolder("%flag%", flagName)
                .setPlaceHolder("%illegalValue%", value.toString())
                .build();
    }
}