package me.mrgeneralq.sleepmost.core.statics;

import org.bukkit.Bukkit;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

/**
 * Verifies {@link ServerVersion} version detection without a running server.
 *
 * <p>{@link Bukkit} is statically mocked purely so that referencing the
 * {@code ServerVersion} enum (whose static initializer calls Bukkit) does not
 * fail outside of a live server. The assertions themselves exercise the pure,
 * side-effect free {@link ServerVersion#resolve(String, String)} method.
 */
class ServerVersionTest {

    @Test
    void calendarVersionSchemeMapsToLatestKnownVersion() {
        try (MockedStatic<Bukkit> ignored = mockStatic(Bukkit.class)) {
            // The exact server version from the crash report.
            assertEquals(ServerVersion.V1_21,
                    ServerVersion.resolve("26.1.2-R0.1-SNAPSHOT", "git-Paper-63 (MC: 26.1.2)"));

            // Any future calendar year is newer than every version we know about.
            assertEquals(ServerVersion.V1_21,
                    ServerVersion.resolve("27.0.1-R0.1-SNAPSHOT", "git-Paper-1 (MC: 27.0.1)"));
        }
    }

    @Test
    void legacyVersionsAreDetectedByName() {
        try (MockedStatic<Bukkit> ignored = mockStatic(Bukkit.class)) {
            assertEquals(ServerVersion.V1_21,
                    ServerVersion.resolve("1.21.4-R0.1-SNAPSHOT", "git-Paper (MC: 1.21.4)"));
            assertEquals(ServerVersion.V1_21,
                    ServerVersion.resolve("1.21.11-R0.1-SNAPSHOT", "git-Spigot (MC: 1.21.11)"));
            assertEquals(ServerVersion.V1_16,
                    ServerVersion.resolve("1.16.5-R0.1-SNAPSHOT", "git-Bukkit (MC: 1.16.5)"));
            assertEquals(ServerVersion.V1_8,
                    ServerVersion.resolve("1.8.8-R0.1-SNAPSHOT", "git-Bukkit (MC: 1.8.8)"));
        }
    }

    @Test
    void unparseableOrNullVersionResolvesToUnknownWithoutThrowing() {
        try (MockedStatic<Bukkit> ignored = mockStatic(Bukkit.class)) {
            assertEquals(ServerVersion.UNKNOWN, ServerVersion.resolve("garbage", "garbage"));
            assertEquals(ServerVersion.UNKNOWN, ServerVersion.resolve(null, null));
        }
    }

    /**
     * Directly guards the root cause of the reported NullPointerException:
     * every version (including UNKNOWN) must have a non-null healer, otherwise
     * {@link ServerVersion#healToMaxHP} throws.
     */
    @Test
    void everyVersionHasANonNullHealer() throws Exception {
        try (MockedStatic<Bukkit> ignored = mockStatic(Bukkit.class)) {
            Field healerField = ServerVersion.class.getDeclaredField("maxHPHealer");
            healerField.setAccessible(true);

            for (ServerVersion version : ServerVersion.values()) {
                assertNotNull(healerField.get(version),
                        "maxHPHealer must never be null (was null for " + version.name() + ")");
            }
        }
    }
}
