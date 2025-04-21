package me.mrgeneralq.sleepmost.models.enums;

/**
 * Enum that is used for flags, this is then used to show the friendly name of the enum.
 * This means we can create a flexible way to handle flags with enum values.
 */
public interface FriendlyNamed {
    String friendlyName();
}

