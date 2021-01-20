package me.mrgeneralq.sleepmost.services;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.*;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SleepServiceTest {

    //non mock
    private ISleepService sleepService;

    @Before
    public void setUp() throws Exception {

        this.sleepService = new SleepService(
                Mockito.mock(Sleepmost.class),
                Mockito.mock(IConfigService.class),
                Mockito.mock(IConfigRepository.class),
                Mockito.mock(IFlagsRepository.class),
                Mockito.mock(IFlagService.class)
        );
    }

    @Test
    public void getCurrentSkipCause() {

        World world = Mockito.mock(World.class);

        when(world.getTime()).thenReturn(20000L);
        when(world.isThundering()).thenReturn(true);

        assertTrue("time is night",sleepService.isNight(world));
        assertEquals("When night, skip cause is always night", SleepSkipCause.NIGHT_TIME, sleepService.getCurrentSkipCause(world));

        when(world.getTime()).thenReturn(0L);
        when(world.isThundering()).thenReturn(true);

        assertEquals("when time is day, and thundering, storm should be skipped", SleepSkipCause.STORM, sleepService.getCurrentSkipCause(world));

        when(world.getTime()).thenReturn(0L);
        when(world.isThundering()).thenReturn(false);

        assertEquals("when time is day, and no thunder, sleep should be unknown aka invalid", SleepSkipCause.UNKNOWN, sleepService.getCurrentSkipCause(world));
    }
}