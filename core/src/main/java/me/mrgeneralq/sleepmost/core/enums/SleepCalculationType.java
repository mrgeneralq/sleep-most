package me.mrgeneralq.sleepmost.core.enums;

public enum SleepCalculationType implements FriendlyNamed {
    PERCENTAGE_REQUIRED("percentage"),
    PLAYERS_REQUIRED("players"),
    ;

    private final String friendlyName;

    SleepCalculationType(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public String friendlyName() {
        return friendlyName;
    }
}
