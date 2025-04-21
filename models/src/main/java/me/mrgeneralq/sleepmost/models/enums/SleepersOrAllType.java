package me.mrgeneralq.sleepmost.models.enums;

public enum SleepersOrAllType implements FriendlyNamed {
    SLEEPERS("sleepers"),
    ALL("all"),
    ;

    private final String friendlyName;

    SleepersOrAllType(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public String friendlyName() {
        return friendlyName;
    }
}
