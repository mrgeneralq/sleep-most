package me.mrgeneralq.sleepmost.statics;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public enum ServerVersion
{
    UNKNOWN, V1_8, V1_9, V1_10, V1_11, V1_12, V1_13, V1_14, V1_15, V1_16, V1_17;

    private final String name;

    //settings
    private boolean supportsHexColors;
    private boolean supportsTitles;
    private boolean sleepCalculatedDifferently;

    //the cached current server version
    public static final ServerVersion CURRENT_VERSION;

    public static final String UPDATE_URL = "https://www.spigotmc.org/resources/sleep-most-1-8-1-16-1-configurable-messages-and-percentage.60623/";

    //cached for internal usage
    private static final ServerVersion[] VALUES = values();

    static
    {
        CURRENT_VERSION = computeServerVersion();

        forVersionsFrom(V1_16, version -> version.supportsHexColors = true);
        forVersionsFrom(V1_14, version -> version.supportsTitles = true);
        forVersionsUntil(V1_13, version -> version.sleepCalculatedDifferently = true);
    }
    ServerVersion()
    {
        this.name = WordUtils.capitalizeFully(name().substring(1).toLowerCase().replace('_', '.'));
    }
    public String getName()
    {
        return this.name;
    }
    public boolean supportsHexColors()
    {
        return this.supportsHexColors;
    }
    public boolean supportsTitles()
    {
        return this.supportsTitles;
    }
    public boolean sleepCalculatedDifferently()
    {
        return this.sleepCalculatedDifferently;
    }

    //Setup methods
    private static ServerVersion computeServerVersion()
    {
        return Arrays.stream(VALUES)
                .filter(version -> Bukkit.getVersion().contains(version.getName()))
                .findFirst()
                .orElse(UNKNOWN);
    }
    private static void forVersionsFrom(ServerVersion minimum, Consumer<ServerVersion> action)
    {
        versionsStream()
                .filter(version -> version.ordinal() >= minimum.ordinal())
                .forEach(action);
    }
    private static void forVersionsUntil(ServerVersion maximum, Consumer<ServerVersion> action)
    {
        versionsStream()
                .filter(version -> version.ordinal() <= maximum.ordinal())
                .forEach(action);
    }
    private static Stream<ServerVersion> versionsStream()
    {
        return Arrays.stream(VALUES)
                .filter(version -> version != UNKNOWN);
    }
}