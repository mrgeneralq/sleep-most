package me.mrgeneralq.sleepmost.mappers;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.models.ConfigMessage;
import org.bukkit.ChatColor;

import java.util.*;

public class MessageMapper {

    private static MessageMapper mapper;
    private final Map<MessageKey, ConfigMessage> messages = new LinkedHashMap<>();

    private MessageMapper() {}

    public static MessageMapper getMapper(){
        if(mapper == null)
            mapper = new MessageMapper();
        return mapper;
    }

    public void loadMessages(){

        this.messages.put(MessageKey.PREFIX, new ConfigMessage("prefix", "&7[&bSleep-Most&7]"));
        this.messages.put(MessageKey.NIGHT_SKIPPED, new ConfigMessage("user.night-skipped-chat", "&aGood morning, %player%!"));
        this.messages.put(MessageKey.STORM_SKIPPED, new ConfigMessage("user.storm-skipped-chat", "&aThe sky is clearing!"));

        this.messages.put(MessageKey.PLAYERS_LEFT_TO_SKIP_NIGHT, new ConfigMessage("user.players-left-night", "&bSleeping to skip night &f[&c%sleeping%/%required%&f]&b!"));
        this.messages.put(MessageKey.PLAYERS_LEFT_TO_SKIP_STORM, new ConfigMessage("user.players-left-storm", "&bSleeping to skip the storm &f[&c%sleeping%/%required%&f]&b!"));
        this.messages.put(MessageKey.SLEEP_PREVENTED, new ConfigMessage("user.sleep-prevented", "&cYou cannot sleep in this world!"));
        this.messages.put(MessageKey.NO_SLEEP_THUNDERSTORM, new ConfigMessage("user.no-sleep-storm", "&cYou cannot sleep during a thunderstorm"));
        this.messages.put(MessageKey.BOSS_BAR_TITLE, new ConfigMessage("boss-bar.title", "&bPlayers sleeping to skip the %skip-cause% &f[&c%sleeping-count%&f/&c%sleeping-required-count%&f]"));
        this.messages.put(MessageKey.NO_CONSOLE_COMMAND, new ConfigMessage("admin.no-console-command", "&cThis command cannot be executed from the console"));
        this.messages.put(MessageKey.NO_PERMISSION_COMMAND, new ConfigMessage("admin.no-permission-command", "&cYou do not have permission to do this"));
        this.messages.put(MessageKey.UNKNOWN_COMMAND, new ConfigMessage("admin.unknown-command", "&cUnknown command"));
        this.messages.put(MessageKey.CONFIG_RELOADED, new ConfigMessage("admin.config-reloaded", "&aYou successfully reloaded Sleep-Most plugin"));
        this.messages.put(MessageKey.ALREADY_ENABLED_FOR_WORLD, new ConfigMessage("admin.already-enabled-for-world", "&cSleep-Most is already enabled in &a%world%"));
        this.messages.put(MessageKey.ALREADY_DISABLED_FOR_WORLD, new ConfigMessage("admin.already-disabled-for-world", "&cSleep-Most is already disabled in &a%world%"));
        this.messages.put(MessageKey.ENABLED_FOR_WORLD, new ConfigMessage("admin.enabled-for-world", "&bSleep-Most is now enabled in &a%world%"));
        this.messages.put(MessageKey.DISABLED_FOR_WORLD, new ConfigMessage("admin.disabled-for-world", "&cSleep-Most is now disabled in &a%world%"));
        this.messages.put(MessageKey.NOT_ENABLED_FOR_WORLD, new ConfigMessage("admin.not-enabled-for-world", "&cSleep-Most is not enabled in &a%world%&c. Enable it with &a/sm enable"));
        this.messages.put(MessageKey.CURRENTLY_DISABLED, new ConfigMessage("admin.currently-disabled", "&bSleepmost is currently &cdisabled &bfor this world. Type &a/sm enable &bto enable sleepmost for this world"));
        this.messages.put(MessageKey.CANNOT_SLEEP_NOW, new ConfigMessage("user.cannot-sleep-now", "&cYou cannot sleep right now"));
        this.messages.put(MessageKey.ALL_FLAGS_RESET_SUCCESS, new ConfigMessage("flag.all-reset-success", "&cAll flags were successfully reset"));
        this.messages.put(MessageKey.SLEEP_SUCCESS, new ConfigMessage("user.sleep-success", "&bYou are now asleep"));
        this.messages.put(MessageKey.NO_LONGER_SLEEPING, new ConfigMessage("user.no-longer-sleeping", "&cYou are no longer sleeping"));
        this.messages.put(MessageKey.NO_BED_LOCATION_SET, new ConfigMessage("user.no-bed-location-set", "&cThere is no bed location set"));
        this.messages.put(MessageKey.TELEPORTED_TO_BED, new ConfigMessage("user.teleported-to-bed", "&bYou were teleported to your bed"));
        this.messages.put(MessageKey.SLEEP_CMD_DISABLED, new ConfigMessage("user.sleep-command-disabled", "&cThe sleep-command is disabled"));
        this.messages.put(MessageKey.ONE_PLAYER_SLEEP_SET, new ConfigMessage("admin.one-player-sleep-set", "&bOne player sleep has been configured"));
        this.messages.put(MessageKey.SPECIFY_PLAYER, new ConfigMessage("admin.specify-player", "&cPlease specify with the player username"));
        this.messages.put(MessageKey.TARGET_NOT_SLEEPING, new ConfigMessage("admin.target-not-sleeping", "&cThe player is not sleeping"));
        this.messages.put(MessageKey.TARGET_NOT_ONLINE, new ConfigMessage("admin.target-not-online", "&cThe player is not online"));
        this.messages.put(MessageKey.INSOMNIA_NOT_SLEEPY, new ConfigMessage("user.insomnia-not-sleepy", "&cYou don`t feel sleepy at the moment"));
        this.messages.put(MessageKey.INSOMNIA_FEELING_SLEEPY, new ConfigMessage("user.insomnia-feeling-sleepy", "&bYou start feeling sleepy ..."));

        this.messages.put(MessageKey.INSOMNIA_ALREADY_ENABLED, new ConfigMessage("admin.insomnia-already-enabled", "&cInsomnia is already enabled in this world"));
        this.messages.put(MessageKey.INSOMNIA_ENABLED, new ConfigMessage("admin.insomnia-enabled", "&bInsomnia is enabled"));


        this.messages.put(MessageKey.CMD_ONLY_DURING_NIGHT, new ConfigMessage("admin.only-during-night-command", "&cThis command can only be used during the night"));
        this.messages.put(MessageKey.KICK_OUT_BED, new ConfigMessage("admin.kick-out-bed", "&c&l[kick out]"));
        this.messages.put(MessageKey.KICKED_PLAYER_FROM_BED, new ConfigMessage("user.kicked-player-from-bed", "&bYou kicked &a%player% &bout of their bed"));
        this.messages.put(MessageKey.YOU_ARE_KICKED_FROM_BED, new ConfigMessage("user.kicked-from-bed", "&cYou were kicked out from your bed"));
        this.messages.put(MessageKey.KICKING_NOT_ALLOWED, new ConfigMessage("user.kicking-not-allowed", "&cYou do not have permission to kick this player out of their bed"));
        this.messages.put(MessageKey.FLAG_SET_IN_WORLD, new ConfigMessage("flag.set-in-world", "&bThe &e%flag% &bflag value in your world is &e%value%&b."));
        this.messages.put(MessageKey.FLAG_RESET_IN_WORLD, new ConfigMessage("flag.reset-in-world", "&bThe &e%flag% &bflag has been reset to &e%default-value%"));
        this.messages.put(MessageKey.FLAG_DOES_NOT_EXIST, new ConfigMessage("flag.not-exist", "&cThis flag does not exist"));
        //TITLES
        this.messages.put(MessageKey.NIGHT_SKIPPED_TITLE, new ConfigMessage("user.night-skipped-title", "&aNight skipped"));
        this.messages.put(MessageKey.NIGHT_SKIPPED_SUBTITLE, new ConfigMessage("user.night-skipped-subtitle", "&bGood morning sunshine!"));
        this.messages.put(MessageKey.STORM_SKIPPED_TITLE, new ConfigMessage("user.storm-skipped-title", "&eStorm skipped"));
        this.messages.put(MessageKey.STORM_SKIPPED_SUBTITLE, new ConfigMessage("user.storm-skipped-subtitle", "&bWhat a weather..."));

        this.messages.put(MessageKey.SLEEP_PREVENTED_LONGER_NIGHT, new ConfigMessage("user.longer-night.sleep-prevented", "&cThe night is longer than usual, you don't feel sleepy yet"));

        this.messages.put(MessageKey.CLOCK_TITLE, new ConfigMessage("clock.title",  "&b%time%"));
        this.messages.put(MessageKey.CLOCK_SUBTITLE, new ConfigMessage("clock.subtitle",  "&a>>>"));
    }

    public ConfigMessage getMessage(MessageKey message){
        return this.messages.get(message);
    }

    public List<ConfigMessage> getAllMessages(){
        return new ArrayList<>(this.messages.values());
    }
}
