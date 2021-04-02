package me.mrgeneralq.sleepmost.services;

import junit.framework.Assert;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.flags.HealFlag;
import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.interfaces.IUpdateRepository;
import me.mrgeneralq.sleepmost.interfaces.IUpdateService;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import static org.mockito.Mockito.mock;


@RunWith(PowerMockRunner.class)
@PrepareForTest({PluginDescriptionFile.class})
public class UpdateServiceTest {

    private IUpdateService updateService;

    //mock
    private IUpdateRepository updateRepository;
    private Sleepmost sleepmost;
    private IConfigService configService;
    private PluginDescriptionFile pluginDescriptionFile;



    @Before
    public void setUp() {
        this.updateRepository = mock(IUpdateRepository.class);
        this.sleepmost = mock(Sleepmost.class);
        this.configService = mock(IConfigService.class);
        this.pluginDescriptionFile = mock(PluginDescriptionFile.class);

        this.updateService = new UpdateService(this.updateRepository, this.sleepmost, this.configService);

    }



    @Test
    public void hasUpdate() {


        String testRemoteVersion = "1.8.0";
        String testCurrentVersion = "1.8.0";

        when(updateRepository.getLatestVersion()).thenReturn(testRemoteVersion);

        assertFalse("There is no update with equal strings",this.updateService.hasUpdate(testCurrentVersion));

        testRemoteVersion = "1.8.0";
        testCurrentVersion = "1.9.0";
        when(updateRepository.getLatestVersion()).thenReturn(testRemoteVersion);

        assertFalse("There is no update when you have a more recent version", this.updateService.hasUpdate(testCurrentVersion));


        testRemoteVersion = "1.12.1";
        testCurrentVersion = "1.12";
        when(updateRepository.getLatestVersion()).thenReturn(testRemoteVersion);

        assertTrue("When the remote updat version contains more levels, it should still evaluate to true.", this.updateService.hasUpdate(testCurrentVersion));






/*
        try {
            PowerMockito.doReturn(pluginDescriptionFile).when(Sleepmost.class, Sleepmost.class.getDeclaredMethod("getDescription"));

        }catch (Exception ex){
        }

 */
     //   when(sleepmost.toString()).thenReturn("a");

      //  when(this.sleepmost.getDescription()).thenReturn(mock(PluginDescriptionFile.class));


    //    assertEquals("Current version matches","1.5.2" , testCurrentVersion);

    }

    @Test
    public void getCurrentVersion() {
    }

    @Test
    public void getCachedUpdateVersion() {
    }

}