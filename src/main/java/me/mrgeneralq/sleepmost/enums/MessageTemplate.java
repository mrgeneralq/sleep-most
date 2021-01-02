package me.mrgeneralq.sleepmost.enums;

public enum MessageTemplate {
    NO_PERMISSION("&cYou don't have permission to that command!"),
    UNKNOWN_COMMAND("&cUnknown command! Type /sleepmost to get a list of available commands"),
    CONFIG_RELOADED("&bSleepmost config reloaded!"),
    ONLY_PLAYERS_COMMAND("&cThis command can only be executed by a player!"),
    ALREADY_ENABLED_FOR_WORLD("&cSleepmost is already enabled for this world!"),
    ALREADY_DISABLED_FOR_WORLD("&cSleepmost is already disabled for this world!"),
    ENABLED_FOR_WORLD("&bSleepmost is now &aenabled &bfor this world!"),
    DISABLED_FOR_WORLD("&bSleepmost is now &cdisabled &bfor this world!"),
    CURRENTLY_DISABLED ("&bSleepmost is currently &cdisabled &bfor this world. Type &a/sm enable &bto enable sleepmost for this world.");

    private final String message;
    private final boolean usePrefix;

    MessageTemplate(String message, boolean usePrefix)
    {
        this.message = message;
        this.usePrefix = usePrefix;
    }
    MessageTemplate(String message)
    {
        this(message, false);
    }

    public String getMessage() {
        return message;
    }

    public boolean usesPrefix() {
        return usePrefix;
    }
}