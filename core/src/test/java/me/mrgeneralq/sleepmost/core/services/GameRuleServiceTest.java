package me.mrgeneralq.sleepmost.core.services;

import org.bukkit.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Guards the fix for the {@code GameRule.ADVANCE_TIME} crash: on Minecraft
 * versions that don't have the gamerule, setting it must not abort plugin
 * enable.
 */
class GameRuleServiceTest {

    @Test
    void setAdvanceTimeSwallowsMissingGameRule() {
        World world = mock(World.class);
        // Simulate an older server where the gamerule field/op is unavailable.
        doThrow(new NoSuchFieldError("simulated missing advanceTime gamerule"))
                .when(world).setGameRule(any(), any());

        GameRuleService service = new GameRuleService();

        assertDoesNotThrow(() -> service.setAdvanceTime(world, true));
    }
}
