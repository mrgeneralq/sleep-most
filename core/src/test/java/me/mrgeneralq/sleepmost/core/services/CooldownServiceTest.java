package me.mrgeneralq.sleepmost.core.services;

import me.mrgeneralq.sleepmost.core.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.core.interfaces.ICooldownRepository;
import me.mrgeneralq.sleepmost.core.interfaces.ICooldownService;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Server-free tests for the sleep-command cooldown logic.
 */
class CooldownServiceTest {

    private ICooldownRepository cooldownRepository;
    private IConfigRepository configRepository;
    private ICooldownService cooldownService;
    private Player player;

    @BeforeEach
    void setUp() {
        this.cooldownRepository = mock(ICooldownRepository.class);
        this.configRepository = mock(IConfigRepository.class);
        this.player = mock(Player.class);
        this.cooldownService = new CooldownService(this.cooldownRepository, this.configRepository);
    }

    @Test
    void cooldownEnabledDependsOnConfiguredValue() {
        when(this.configRepository.getCooldown()).thenReturn(30);
        assertTrue(this.cooldownService.cooldownEnabled(), "A positive cooldown means cooldowns are enabled");

        when(this.configRepository.getCooldown()).thenReturn(0);
        assertTrue(this.cooldownService.cooldownEnabled(), "A zero cooldown is still enabled (0 > -1)");

        when(this.configRepository.getCooldown()).thenReturn(-1);
        assertFalse(this.cooldownService.cooldownEnabled(), "-1 disables cooldowns");
    }

    @Test
    void notCoolingDownWhenPlayerHasNoRecord() {
        when(this.cooldownRepository.contains(this.player)).thenReturn(false);
        assertFalse(this.cooldownService.isCoolingDown(this.player));
    }

    @Test
    void coolingDownWhenLastUseIsWithinTheCooldownWindow() {
        when(this.cooldownRepository.contains(this.player)).thenReturn(true);
        when(this.cooldownRepository.getPlayerCooldown(this.player)).thenReturn(System.currentTimeMillis());
        when(this.configRepository.getCooldown()).thenReturn(30);

        assertTrue(this.cooldownService.isCoolingDown(this.player),
                "A cooldown started just now is still active");
    }

    @Test
    void notCoolingDownWhenTheCooldownWindowHasElapsed() {
        when(this.cooldownRepository.contains(this.player)).thenReturn(true);
        when(this.cooldownRepository.getPlayerCooldown(this.player))
                .thenReturn(System.currentTimeMillis() - 60_000L); // 60 seconds ago
        when(this.configRepository.getCooldown()).thenReturn(30);

        assertFalse(this.cooldownService.isCoolingDown(this.player),
                "A cooldown that started 60s ago has expired for a 30s window");
    }

    @Test
    void startCooldownDelegatesToRepository() {
        this.cooldownService.startCooldown(this.player);
        verify(this.cooldownRepository).setCooldown(this.player);
    }

    @Test
    void removeCooldownDelegatesToRepository() {
        this.cooldownService.removeCooldown(this.player);
        verify(this.cooldownRepository).removeCooldown(this.player);
    }
}
