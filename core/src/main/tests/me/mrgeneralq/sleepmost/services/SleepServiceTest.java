import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SleepServiceTest {

    //non mock
    private ISleepService sleepService;

    private Sleepmost mockSleepmost;
    private PluginManager pluginManager;
    private IConfigService mockConfigService;
    private IConfigRepository mockConfigRepository;
    private IFlagsRepository mockFlagRepository;
    private IFlagService mockFlagService;
    private IPlayerService playerService;
    private IDebugService debugService;
    private ISleepMostWorldService sleepMostWorldService;
    private IHookService hookService;
    private IMessageService messageService;
    private ISleepMostPlayerService mockPlayerService;

    //@BeforeEach
    public void setUp() {
        this.mockPlayerService = mock(ISleepMostPlayerService.class);
        this.debugService = new DebugService(this.mockPlayerService, this.mockConfigService);

        this.hookService = mock(IHookService.class);
        this.mockSleepmost = mock(Sleepmost.class);

        this.mockConfigService = mock(IConfigService.class);
        this.mockConfigRepository = mock(IConfigRepository.class);
        this.mockFlagRepository = mock(IFlagsRepository.class);
        this.mockFlagService = mock(IFlagService.class);
        this.playerService = mock(IPlayerService.class);
        this.messageService = mock(IMessageService.class);

        this.sleepService = new SleepService(this.mockSleepmost, this.mockConfigService, this.mockConfigRepository,this.mockFlagRepository,this.mockFlagService, this.playerService, this.debugService, this.sleepMostWorldService, this.hookService, this.messageService);
    }

    //@Test
    public void getCurrentSkipCause() {

        World world = mock(World.class);

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



    //@Test
    public void getPlayerCountInWorld() {

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);


        List<Player> playerList = Arrays.asList(player1,player2,player3);
        World mockWorld = mock(World.class);

        Location location = new Location(mockWorld, 5.0,-5.0, 10.0);

        when(player1.getLocation()).thenReturn(location);
        when(player2.getLocation()).thenReturn(location);
        when(player3.getLocation()).thenReturn(location);

        when(mockWorld.getPlayers()).thenReturn(playerList);

        ExemptCreativeFlag exemptCreativeFlag = mock(ExemptCreativeFlag.class);
        ExemptSpectatorFlag exemptSpectatorFlag = mock(ExemptSpectatorFlag.class);
        ExemptBelowYFlag exemptBelowYFlag = mock(ExemptBelowYFlag.class);
        UseExemptFlag useExemptFlag = mock(UseExemptFlag.class);
        ExemptFlyingFlag exemptFlyingFlag = mock(ExemptFlyingFlag.class);

        when(this.mockFlagRepository.getExemptCreativeFlag()).thenReturn(exemptCreativeFlag);
        when(this.mockFlagRepository.getExemptSpectatorFlag()).thenReturn(exemptSpectatorFlag);
        when(this.mockFlagRepository.getExemptBelowYFlag()).thenReturn(exemptBelowYFlag);
        when(this.mockFlagRepository.getUseExemptFlag()).thenReturn(useExemptFlag);
        when(this.mockFlagRepository.getExemptFlyingFlag()).thenReturn(exemptFlyingFlag);

        when(this.playerService.isRealPlayer(player1)).thenReturn(true);
        when(this.playerService.isRealPlayer(player2)).thenReturn(true);
        when(this.playerService.isRealPlayer(player3)).thenReturn(true);

        when(exemptCreativeFlag.getValueAt(mockWorld)).thenReturn(true);
        when(exemptSpectatorFlag.getValueAt(mockWorld)).thenReturn(false);
        when(exemptBelowYFlag.getValueAt(mockWorld)).thenReturn(-1);
        when(useExemptFlag.getValueAt(mockWorld)).thenReturn(false);
        when(exemptFlyingFlag.getValueAt(mockWorld)).thenReturn(false);

        when(player1.getGameMode()).thenReturn(GameMode.CREATIVE);
        when(player2.getGameMode()).thenReturn(GameMode.SURVIVAL);
        when(player3.getGameMode()).thenReturn(GameMode.SURVIVAL);

        assertEquals("Exempt players that are in creative mode",2,this.sleepService.getPlayerCountInWorld(mockWorld));

        when(exemptCreativeFlag.getValueAt(mockWorld)).thenReturn(false);
        when(exemptSpectatorFlag.getValueAt(mockWorld)).thenReturn(true);
        when(exemptBelowYFlag.getValueAt(mockWorld)).thenReturn(-1);
        when(useExemptFlag.getValueAt(mockWorld)).thenReturn(false);

        when(player1.getGameMode()).thenReturn(GameMode.SPECTATOR);
        when(player2.getGameMode()).thenReturn(GameMode.SURVIVAL);
        when(player3.getGameMode()).thenReturn(GameMode.SURVIVAL);
        assertEquals("Exempt players that are in Spectator mode", 2, this.sleepService.getPlayerCountInWorld(mockWorld));


        when(exemptCreativeFlag.getValueAt(mockWorld)).thenReturn(false);
        when(exemptSpectatorFlag.getValueAt(mockWorld)).thenReturn(false);
        when(exemptBelowYFlag.getValueAt(mockWorld)).thenReturn(-1);
        when(useExemptFlag.getValueAt(mockWorld)).thenReturn(false);

        when(this.playerService.isRealPlayer(player1)).thenReturn(true);
        when(this.playerService.isRealPlayer(player2)).thenReturn(false);
        when(this.playerService.isRealPlayer(player3)).thenReturn(true);
        assertEquals("Make sure that only real players are included", 2, this.sleepService.getPlayerCountInWorld(mockWorld));
    }
}