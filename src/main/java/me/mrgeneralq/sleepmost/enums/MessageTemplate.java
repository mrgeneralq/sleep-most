package me.mrgeneralq.sleepmost.enums;

public enum MessageTemplate {
    NO_PERMISSION("&cYou don't have permission to that command!",false),
    UNKNOWN_COMMAND("&cUnknown command! Type /sleepmost to get a list of available commands", false),
    CONFIG_RELOADED("&bSleepmost config reloaded!",false),
    ONLY_PLAYERS_COMMAND("&cThis command can only be executed by a player!",false),
    ALREADY_ENABLED_FOR_WORLD("&cSleepmost is already enabled for this world!",false),
    ALREADY_DISABLED_FOR_WORLD("&cSleepmost is already disabled for this world!",false),
    ENABLED_FOR_WORLD("&bSleepmost is now &aenabled &bfor this world!", false),
    DISABLED_FOR_WORLD("&bSleepmost is now &cdisabled &bfor this world!",false),
    CURRENTLY_DISABLED ("&bSleepmost is currently &cdisabled &bfor this world. Type &a/sm enable &bto enable sleepmost for this world.",false);


    private final String message;
    private final boolean usePrefix;

    MessageTemplate(String message, boolean usePrefix)
    {
        this.message = message;
        this.usePrefix = usePrefix;
    }

    public String getMessage() {
        return message;
    }

    public boolean usesPrefix() {
        return usePrefix;
    }
}
