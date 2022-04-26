package me.mrgeneralq.sleepmost.mappers;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.models.ConfigMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageMapper {

    private static MessageMapper mapper;
    private final Map<MessageKey, ConfigMessage> messages = new HashMap<>();

    private MessageMapper() {}

    public static MessageMapper getMapper(){
        if(mapper == null)
            mapper = new MessageMapper();
        return mapper;
    }

    public void loadMessages(){

        this.messages.put(MessageKey.PREFIX, new ConfigMessage("prefix", "&7[&bSleep-Most&7]"));
        this.messages.put(MessageKey.NIGHT_SKIPPED, new ConfigMessage("user.night-skipped.title", "&aNight skipped"));
        this.messages.put(MessageKey.STORM_SKIPPED, new ConfigMessage("user.storm-skipped.title", "&eStorm skipped"));
        this.messages.put(MessageKey.PLAYERS_LEFT_TO_SKIP_NIGHT, new ConfigMessage("user.players-left-night", "&bSleeping to skip night &f[&c%sleeping%/%required%&f]&b!"));
        this.messages.put(MessageKey.PLAYERS_LEFT_TO_SKIP_STORM, new ConfigMessage("user.players-left-storm", "&bSleeping to skip the storm &f[&c%sleeping%/%required%&f]&b!"));
        this.messages.put(MessageKey.SLEEP_PREVENTED, new ConfigMessage("user.sleep-prevented", "&cYou cannot sleep in this world!"));
        this.messages.put(MessageKey.NO_SLEEP_THUNDERSTORM, new ConfigMessage("user.no-sleep-storm", "&cYou cannot sleep during a thunderstorm"));
        this.messages.put(MessageKey.BOSS_BAR_TITLE, new ConfigMessage("boss-bar.title", "&bPlayers sleeping to skip the %skip-cause% &f[&c%sleeping-count%&f/&c%sleeping-required-count%&f]"));
        this.messages.put(MessageKey.NO_CONSOLE_COMMAND, new ConfigMessage("admin.no-console-command", "&cThis command cannot be executed from the console"));
        this.messages.put(MessageKey.NO_PERMISSION_COMMAND, new ConfigMessage("admin.no-permission-command", "&cYou do not have permission to do this"));
        this.messages.put(MessageKey.UNKNOWN_COMMAND, new ConfigMessage("admin.unknown-command", "&cUnknown command"));
        this.messages.put(MessageKey.CONFIG_RELOADED, new ConfigMessage("admin.config-reloaded", "&aYou successfully reloaded Sleep-Most plugin"));
        //this.messages.put(MessageKey.ONLY_PLAYERS_COMMAND, new ConfigMessage("", "default message"));
        this.messages.put(MessageKey.ALREADY_ENABLED_FOR_WORLD, new ConfigMessage("admin.already-enabled-for-world", "&cSleep-Most is already enabled in %world%"));
        this.messages.put(MessageKey.ALREADY_DISABLED_FOR_WORLD, new ConfigMessage("admin.already-disabled-for-world", "&cSleep-Most is already disabled in %world%"));
        this.messages.put(MessageKey.ENABLED_FOR_WORLD, new ConfigMessage("admin.enabled-for-world", "&cSleep-Most is now enabled in %world%"));
        this.messages.put(MessageKey.DISABLED_FOR_WORLD, new ConfigMessage("admin.disabled-for-world", "&cSleep-Most is now disabled in %world%"));
        this.messages.put(MessageKey.NOT_ENABLED_FOR_WORLD, new ConfigMessage("admin.not-enabled-for-world", "&cSleep-Most is not enabled in %world%. Enable it with &a/sm enable"));
        this.messages.put(MessageKey.CURRENTLY_DISABLED, new ConfigMessage("admin.currently-disabled", "&bSleepmost is currently &cdisabled &bfor this world. Type &a/sm enable &bto enable sleepmost for this world"));
        this.messages.put(MessageKey.CANNOT_SLEEP_NOW, new ConfigMessage("user.cannot-sleep-now", "&cYou cannot sleep right now"));
        this.messages.put(MessageKey.FLAGS_RESET_SUCCESS, new ConfigMessage("flag.flag-reset-success", "&cAll flags were successfully reset"));
        this.messages.put(MessageKey.SLEEP_SUCCESS, new ConfigMessage("user.sleep-success", "&aYou are now asleep"));
        this.messages.put(MessageKey.NO_LONGER_SLEEPING, new ConfigMessage("user.no-longer-sleeping", "&cYou are no longer sleeping"));
        this.messages.put(MessageKey.NO_BED_LOCATION_SET, new ConfigMessage("user.no-bed-location-set", "&cThere is no bed location set"));
        this.messages.put(MessageKey.TELEPORTED_TO_BED, new ConfigMessage("user.teleported-to-bed", "&cYou were teleported to your bed"));
        this.messages.put(MessageKey.SLEEP_CMD_DISABLED, new ConfigMessage("user.sleep-command-disabled", "&cThe sleep-command is disabled"));
        this.messages.put(MessageKey.ONE_PLAYER_SLEEP_SET, new ConfigMessage("admin.one-player-sleep-set", "&cOne player sleep has been configured"));
        this.messages.put(MessageKey.SPECIFY_PLAYER, new ConfigMessage("admin.specify-player", "&cPlease specify with the player username"));
        this.messages.put(MessageKey.TARGET_NOT_SLEEPING, new ConfigMessage("admin.target-not-sleeping", "&cThe player is not sleeping"));
        this.messages.put(MessageKey.TARGET_NOT_ONLINE, new ConfigMessage("admin.target-not-online", "&cThe player is not online"));
        this.messages.put(MessageKey.INSOMNIA_NOT_SLEEPY, new ConfigMessage("user.insomnia-not-sleepy", "&cYou don't feel sleepy at the moment"));
        this.messages.put(MessageKey.INSOMNIA_ALREADY_ENABLED, new ConfigMessage("admin.insomnia-already-enabled", "&cInsomnia is already enabled in this world"));
        this.messages.put(MessageKey.INSOMNIA_ENABLED, new ConfigMessage("admin.insomnia-enabled", "&cInsomnia is enabled"));
        this.messages.put(MessageKey.CMD_ONLY_DURING_NIGHT, new ConfigMessage("admin.only-during-night-command", "&cThis command can only be used during the night"));
        this.messages.put(MessageKey.KICK_OUT_BED, new ConfigMessage("admin.kick-out-bed", "&c&l[kick out]"));
        this.messages.put(MessageKey.KICKED_PLAYER_FROM_BED, new ConfigMessage("user.kicked-player-from-bed", "&cYou kicked %target-player% out from their bed"));
        this.messages.put(MessageKey.YOU_ARE_KICKED_FROM_BED, new ConfigMessage("user.kicked-from-bed", "&cYou were kicked out from your bed"));
        this.messages.put(MessageKey.KICKING_NOT_ALLOWED, new ConfigMessage("user.kicking-not-allowed", "&cYou do not have permission to kick this player out of their bed"));
    }

    public ConfigMessage getMessage(MessageKey message){
        return this.messages.get(message);
    }

    public List<ConfigMessage> getAllMessages(){
        return new ArrayList<>(this.messages.values());
    }
}
