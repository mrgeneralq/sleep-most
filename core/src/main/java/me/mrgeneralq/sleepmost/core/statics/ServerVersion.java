package me.mrgeneralq.sleepmost.core.statics;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum ServerVersion
{
    UNKNOWN, V1_8, V1_9, V1_10, V1_11, V1_12, V1_13, V1_14, V1_15, V1_16, V1_17, V1_18, V1_19, V1_20,V1_21;

    private final String name;
    private MaxHPHealer maxHPHealer;
    private boolean supportsHexColors;
    private boolean supportsTitles;
    private boolean supportsBossBars;
    private boolean hasTimeSkipEvent;
    private boolean supportsClickableText;
    private boolean supportsGameRules;

    public static final ServerVersion CURRENT_VERSION;

    public static final String UPDATE_URL = "https://www.spigotmc.org/resources/60623/";

    //cached for internal usage
    private static final ServerVersion[] VALUES = values();

    static {
        CURRENT_VERSION = computeServerVersion();
        forVersionsFrom(V1_16, version -> version.supportsHexColors = true);
        forVersionsFrom(V1_12, version -> version.supportsTitles = true);
        forVersionsFrom(V1_17, version -> version.supportsClickableText = true);
        forVersionsUntil(V1_9, version -> version.maxHPHealer = MaxHPHealer.LEGACY_HEALER);
        forVersionsFrom(V1_9, version -> version.maxHPHealer = MaxHPHealer.UPDATED_HEALER);
        forVersionsFrom(V1_15, version -> version.hasTimeSkipEvent = true);
        forVersionsFrom(V1_12, version -> version.supportsBossBars = true);
        forVersionsFrom(V1_13, version -> version.supportsGameRules = true);

    }
    ServerVersion() {
        this.name = WordUtils.capitalizeFully(name().substring(1).toLowerCase().replace('_', '.'));
        // Sensible default so healToMaxHP() never NPEs, even for UNKNOWN or an
        // unmatched version. Legacy (< 1.9) versions override this below.
        this.maxHPHealer = MaxHPHealer.UPDATED_HEALER;
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

    public boolean supportsClickableText() {
        return supportsClickableText;
    }

    public boolean supportsGameRules() {
        return supportsGameRules;
    }

    //Setup methods

    /*private static ServerVersion computeServerVersion() {
        return Arrays.stream(VALUES)
                .filter(version -> Bukkit.getVersion().contains(version.getName()))
                .findFirst()
                .orElse(UNKNOWN);
    }
    */

    private static ServerVersion computeServerVersion() {
        return resolve(Bukkit.getBukkitVersion(), Bukkit.getVersion());
    }

    /**
     * Resolves the {@link ServerVersion} for the given raw version strings.
     * Kept package-private and free of any {@link Bukkit} calls so the version
     * detection can be unit tested without a running server.
     *
     * <p>Since 2026 Minecraft uses a calendar-year based versioning scheme
     * (e.g. "26.1.2") instead of the historic "1.x" scheme. Any such release is
     * newer than every "1.x" version we explicitly know about, so it is mapped
     * to the latest known version to inherit its capabilities rather than
     * degrading to {@link #UNKNOWN} (which previously left maxHPHealer null and
     * caused a NullPointerException in {@link #healToMaxHP(Player)}).
     *
     * @param bukkitVersion value of {@link Bukkit#getBukkitVersion()}, e.g. "26.1.2-R0.1-SNAPSHOT"
     * @param serverVersion value of {@link Bukkit#getVersion()}, e.g. "git-Paper (MC: 1.21.4)"
     * @return the resolved version, never {@code null}
     */
    static ServerVersion resolve(String bukkitVersion, String serverVersion) {
        OptionalInt majorVersion = extractMajorVersion(bukkitVersion);

        if (majorVersion.isPresent() && majorVersion.getAsInt() >= 2)
            return getLatestKnownVersion();

        // Legacy "1.x" servers: match the version by its name.
        return matchByLegacyName(serverVersion);
    }

    private static ServerVersion matchByLegacyName(String serverVersion) {
        if (serverVersion == null)
            return UNKNOWN;

        return Arrays.stream(VALUES)
                .filter(version -> version != UNKNOWN)
                .sorted((v1, v2) -> Integer.compare(v2.ordinal(), v1.ordinal()))
                .filter(version -> serverVersion.contains(version.getName()))
                .findFirst()
                .orElse(UNKNOWN);
    }

    /**
     * Extracts the leading (major) number of the given server version string.
     * For legacy releases this is always 1 (e.g. "1.21.4"); for the calendar
     * year based scheme it is the year (e.g. 26 for "26.1.2").
     */
    private static OptionalInt extractMajorVersion(String bukkitVersion) {
        if (bukkitVersion == null)
            return OptionalInt.empty();

        Matcher matcher = Pattern.compile("(\\d+)").matcher(bukkitVersion);
        if (matcher.find())
            return OptionalInt.of(Integer.parseInt(matcher.group(1)));
        return OptionalInt.empty();
    }

    private static ServerVersion getLatestKnownVersion() {
        ServerVersion latest = UNKNOWN;
        for (ServerVersion version : VALUES) {
            if (version != UNKNOWN && version.ordinal() > latest.ordinal())
                latest = version;
        }
        return latest;
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