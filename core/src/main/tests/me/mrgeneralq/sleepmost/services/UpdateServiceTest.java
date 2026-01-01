package me.mrgeneralq.core.services;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;


public class UpdateServiceTest {

    private IUpdateService updateService;

    //mock
    private IUpdateRepository updateRepository;
    private IConfigService configService;

    @Before
    public void setUp() {
        this.updateRepository = mock(IUpdateRepository.class);
        this.configService = mock(IConfigService.class);

        this.updateService = new UpdateService(this.updateRepository, mock(Sleepmost.class), this.configService);

    }


    @Test
    public void hasUpdate() { //great, I have nothing to add/remove ,the only thing that is a risk right now is the repository. We actually need to create test script for that one to :p
        String testCurrentVersion = "1.8.0";
        when(this.updateRepository.getLatestVersion()).thenReturn("1.8.0", "1.12.1", "2.2","2.2.15.5", "1.0");
        when(this.configService.updateCheckerEnabled()).thenReturn(false, true);

        assertFalse("There is no update when the update checker is disabled", this.updateService.hasUpdate(testCurrentVersion));
        assertFalse("There is no update with equal strings", this.updateService.hasUpdate(testCurrentVersion));

        testCurrentVersion = "1.12"; // latest version = 1.12.1
        assertTrue("When the remote update version contains more dots, it should still evaluate to true.", this.updateService.hasUpdate(testCurrentVersion));

        testCurrentVersion = "1.1.5.4"; // latest version = 2.2
        assertTrue("Remote version has more dots than the current version. Remote version is higher then current version.", this.updateService.hasUpdate(testCurrentVersion));

        testCurrentVersion = "3.5"; // latest version = 2.2.15.5
        assertFalse("When the remote version has more dots, and it's lower then the current version, it should still not have an update", this.updateService.hasUpdate(testCurrentVersion));

        testCurrentVersion = "1.2"; // latest version = 1.0
        assertFalse("When the current version is higher then the latest version, there should be no update", this.updateService.hasUpdate(testCurrentVersion));

    }

    @Test
    public void getCurrentVersion() {
    }

    @Test
    public void getCachedUpdateVersion() {
    }

}