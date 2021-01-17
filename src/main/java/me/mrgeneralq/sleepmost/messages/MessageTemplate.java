package me.mrgeneralq.sleepmost.messages;

public enum MessageTemplate {
    NO_PERMISSION("&cYou don't have permission to that command!"),
    UNKNOWN_COMMAND("&cUnknown command! Type /sleepmost to get a list of available commands"),
    CONFIG_RELOADED("&bSleepmost config reloaded!"),
    ONLY_PLAYERS_COMMAND("&cThis command can only be executed by a player!"),
    ALREADY_ENABLED_FOR_WORLD("&cSleepmost is already enabled for this world!"),
    ALREADY_DISABLED_FOR_WORLD("&cSleepmost is already disabled for this world!"),
    ENABLED_FOR_WORLD("&bSleepmost is now &aenabled &bfor this world!"),
    DISABLED_FOR_WORLD("&bSleepmost is now &cdisabled &bfor this world!"),
    CURRENTLY_DISABLED ("&bSleepmost is currently &cdisabled &bfor this world. Type &a/sm enable &bto enable sleepmost for this world."),
    NO_CONSOLE_COMMAND("&cThis command cannot be executed from console!"),
    CANNOT_SLEEP_NOW("&cYou can't sleep now because your world is either not at Night or doesn't have a Storm!"),
    SLEEP_SUCCESS("&aYou are now Sleeping!", true),
    ALREADY_SLEEPING("&cYou are already sleeping", true);

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
