package me.mrgeneralq.sleepmost.core.templates;

public enum MessageTemplate {
    NO_PERMISSION("&cYou don't have permission to that command!"),
    UNKNOWN_COMMAND("&cUnknown command! Type /sleepmost to get a list of available commands"),
    CONFIG_RELOADED("&bSleepmost config reloaded!"),
    ONLY_PLAYERS_COMMAND("&cThis command can only be executed by a player!"),
    ALREADY_ENABLED_FOR_WORLD("&cSleepmost is already enabled for this world!"),
    ALREADY_DISABLED_FOR_WORLD("&cSleepmost is already disabled for this world!"),
    ENABLED_FOR_WORLD("&bSleepmost is now &aenabled &bfor this world!"),
    DISABLED_FOR_WORLD("&bSleepmost is now &cdisabled &bfor this world!"),
    NOT_ENABLED_FOR_WORLD("&cSleepmost is not enabled for this world", true),
    CURRENTLY_DISABLED ("&bSleepmost is currently &cdisabled &bfor this world. Type &a/sm enable &bto enable sleepmost for this world."),
    NO_CONSOLE_COMMAND("&cThis command cannot be executed from console!"),
    CANNOT_SLEEP_NOW("&cYou can't sleep now because your world is either not at Night or doesn't have a Storm!"),
    FLAGS_RESET_SUCCESS("&aAll flags have been reset for the world: &b%world%&a."),
    SLEEP_SUCCESS("&aYou are now Sleeping!", true),
    NO_LONGER_SLEEPING("&eYou are no longer Sleeping!", true),
    NO_BED_LOCATION_SET("&cYou don't have a bed or it is obstructed!", true),
    TELEPORTED_TO_BED("&aYou teleported to your bed!", true),
    SLEEP_CMD_DISABLED("&cYou cannot use the /sleep command in this world!", true),
    ONE_PLAYER_SLEEP_SET("&aOne player sleep has been configured!", true),
    SPECIFY_PLAYER("&cPlease specify a player!"),
    TARGET_NOT_SLEEPING("&cThis player is not asleep!"),
    TARGET_NOT_ONLINE("&cThe target player is not online!"),
    INSOMNIA_NOT_SLEEPY("&cYou don't feel sleepy at the moment"),
    INSOMNIA_ALREADY_ENABLED("&cInsomnia is already enabled for this night", true),
    INSOMNIA_ENABLED("&aInsomnia enabled for the night", true),
    CMD_ONLY_DURING_NIGHT("&cThis command can only be used during the night!");

    private final String rawMessage;
    private final boolean usePrefix;

    MessageTemplate(String rawMessage)
    {
        this(rawMessage, false);
    }
    MessageTemplate(String rawMessage, boolean usePrefix)
    {
        this.rawMessage = rawMessage;
        this.usePrefix = usePrefix;
    }
    public String getRawMessage() {
        return rawMessage;
    }
    public boolean usesPrefix() {
        return usePrefix;
    }
}
