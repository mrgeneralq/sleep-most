package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UpdateServiceTest {

    private IUpdateService updateService;

    //mock
    private IUpdateRepository updateRepository;
    private Sleepmost sleepmost;
    private IConfigService configService;

    @Before
    public void setUp() throws Exception {
        this.updateRepository = Mockito.mock(IUpdateRepository.class);



        this.sleepmost = Mockito.mock(Sleepmost.class);
        this.configService = Mockito.mock(IConfigService.class);

        this.updateService = new UpdateService(this.updateRepository, this.sleepmost, this.configService);




        
    }

    @Test
    public void hasUpdate() {

        String testRemoteVersion = "3.10.14";

        when(updateRepository.getLatestVersion()).thenReturn(testRemoteVersion);
        when(sleepmost.getDescription().getVersion()).thenReturn("3.2.0");

        assertEquals(testRemoteVersion, this.updateService.getCurrentVersion());

    }

    @Test
    public void getCurrentVersion() {
    }

    @Test
    public void getCachedUpdateVersion() {
    }
}