package me.qintinator.sleepmost;

import jdk.nashorn.internal.runtime.linker.Bootstrap;
import me.qintinator.sleepmost.eventlisteners.OnSleep;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISleepFlagService;
import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.services.SleepService;
import me.qintinator.sleepmost.statics.Bootstrapper;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({World.class, Player.class})
public class SleepServiceTest {

    @Test
    public void testSleepingPlayerCountValid() {

        Main mockupMain = PowerMockito.mock(Main.class);
        World mockWorld = mock(World.class);

        List<Player> playerList = new ArrayList<Player>();
        for(int i = 0; i < 5; i++){
            Player p = PowerMockito.mock(Player.class);
            playerList.add(p);
        }

        when(mockWorld.getPlayers()).thenReturn(playerList);
        Bootstrapper bootstrapper = Bootstrapper.getBootstrapper();
        bootstrapper.initialize(mockupMain);
        ISleepService sleepService = bootstrapper.getSleepService();

        assertTrue( "Sleeping player count is below 1",sleepService.getPlayersSleepingCount(mockWorld) > 0);
        assertEquals("Sleeping player count did not match with expected count",1, sleepService.getPlayersSleepingCount(mockWorld));

    }





}
