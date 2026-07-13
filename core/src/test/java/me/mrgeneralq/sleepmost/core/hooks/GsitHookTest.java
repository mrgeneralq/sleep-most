package me.mrgeneralq.sleepmost.core.hooks;

import dev.geco.gsit.api.GSitAPI;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

/**
 * Guards the fix for the GSit {@code NoSuchMethodError}: an incompatible GSit
 * version must not be able to abort sleep skipping.
 */
class GsitHookTest {

    @Test
    void setSleepingPoseSwallowsIncompatibleGsitApi() {
        try (MockedStatic<GSitAPI> gsit = mockStatic(GSitAPI.class)) {
            // Simulate the exact failure from the crash report: the installed
            // GSit exposes a different signature for this method.
            gsit.when(() -> GSitAPI.getPoseByPlayer(any()))
                    .thenThrow(new NoSuchMethodError("simulated GSit API mismatch"));

            GsitHook hook = new GsitHook();
            Player player = mock(Player.class);

            // Before the fix this error propagated up and broke the SleepSkipEvent.
            assertDoesNotThrow(() -> hook.setSleepingPose(player, false));
        }
    }
}
