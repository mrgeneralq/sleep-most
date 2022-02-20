package me.mrgeneralq.sleepmost.statics;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public enum ServerVersion
{
    UNKNOWN, V1_8, V1_9, V1_10, V1_11, V1_12, V1_13, V1_14, V1_15, V1_16, V1_17, V1_18;

    private final String name;
    private MaxHPHealer maxHPHealer;
    private boolean supportsHexColors;
    private boolean supportsTitles;
    private boolean supportsBossBars;
    private boolean hasTimeSkipEvent;

    public static final ServerVersion CURRENT_VERSION;

    public static final String UPDATE_URL = "https://www.spigotmc.org/resources/60623/";

    //cached for internal usage
    private static final ServerVersion[] VALUES = values();

    static {
        CURRENT_VERSION = computeServerVersion();
        forVersionsFrom(V1_16, version -> version.supportsHexColors = true);
        forVersionsFrom(V1_12, version -> version.supportsTitles = true);
        forVersionsUntil(V1_9, version -> version.maxHPHealer = MaxHPHealer.LEGACY_HEALER);
        forVersionsFrom(V1_9, version -> version.maxHPHealer = MaxHPHealer.UPDATED_HEALER);
        forVersionsFrom(V1_15, version -> version.hasTimeSkipEvent = true);
        forVersionsFrom(V1_12, version -> version.supportsBossBars = true);
    }
    ServerVersion() {
        this.name = WordUtils.capitalizeFully(name().substring(1).toLowerCase().replace('_', '.'));
    }

    public String getName() {
        return this.name;
    }

    public boolean supportsHexColors() {
        return this.supportsHexColors;
    }

    public boolean supportsTitles() {
        return this.supportsTitles;
    }

    public void healToMaxHP(Player player) {
        this.maxHPHealer.heal(player);
    }

    public boolean supportsBossBars(){
        return this.supportsBossBars;
    }

    public boolean hasTimeSkipEvent() {
        return hasTimeSkipEvent;
    }

    //Setup methods
    private static ServerVersion computeServerVersion() {
        return Arrays.stream(VALUES)
                .filter(version -> Bukkit.getVersion().contains(version.getName()))
                .findFirst()
                .orElse(UNKNOWN);
    }
    private static void forVersionsFrom(ServerVersion minimum, Consumer<ServerVersion> action) {
        versionsStream()
                .filter(version -> version.ordinal() >= minimum.ordinal())
                .forEach(action);
    }
    private static void forVersionsUntil(ServerVersion maximum, Consumer<ServerVersion> action) {
        versionsStream()
                .filter(version -> version.ordinal() < maximum.ordinal())
                .forEach(action);
    }
    private static Stream<ServerVersion> versionsStream() {
        return Arrays.stream(VALUES)
                .filter(version -> version != UNKNOWN);
    }
}
