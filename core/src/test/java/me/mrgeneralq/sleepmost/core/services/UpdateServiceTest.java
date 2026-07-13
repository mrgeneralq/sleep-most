package me.mrgeneralq.sleepmost.core.services;

import me.mrgeneralq.sleepmost.core.Sleepmost;
import me.mrgeneralq.sleepmost.core.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.core.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.core.interfaces.IUpdateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Server-free tests for the update version-comparison logic. Revived and
 * migrated from the old (disabled) test under {@code src/main/tests}.
 */
class UpdateServiceTest {

    private IUpdateService updateService;
    private IUpdateRepository updateRepository;
    private IConfigService configService;

    @BeforeEach
    void setUp() {
        this.updateRepository = mock(IUpdateRepository.class);
        this.configService = mock(IConfigService.class);
        this.updateService = new UpdateService(this.updateRepository, mock(Sleepmost.class), this.configService);
    }

    @Test
    void hasUpdate() {
        when(this.updateRepository.getLatestVersion()).thenReturn("1.8.0", "1.12.1", "2.2", "2.2.15.5", "1.0");
        when(this.configService.updateCheckerEnabled()).thenReturn(false, true);

        assertFalse(this.updateService.hasUpdate("1.8.0"),
                "There is no update when the update checker is disabled");

        assertFalse(this.updateService.hasUpdate("1.8.0"),
                "There is no update when the versions are equal");

        // latest version = 1.12.1
        assertTrue(this.updateService.hasUpdate("1.12"),
                "When the remote version has more nodes and is higher, it is an update");

        // latest version = 2.2
        assertTrue(this.updateService.hasUpdate("1.1.5.4"),
                "Remote version with fewer nodes but a higher leading node is still an update");

        // latest version = 2.2.15.5
        assertFalse(this.updateService.hasUpdate("3.5"),
                "Remote version with more nodes but a lower leading node is not an update");

        // latest version = 1.0
        assertFalse(this.updateService.hasUpdate("1.2"),
                "When the current version is higher than the latest, there is no update");
    }
}
