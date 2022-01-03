package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepState;
import me.mrgeneralq.sleepmost.events.PlayerSleepStateChangeEvent;
import me.mrgeneralq.sleepmost.interfaces.IBossBarService;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.messages.MessageBuilder;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class PlayerBedLeaveEventListener implements Listener {

    private final IBossBarService bossBarService;
    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final IFlagsRepository flagsRepository;

    public PlayerBedLeaveEventListener(
            IBossBarService bossBarService,
            ISleepService sleepService,
            IMessageService messageService,
            IFlagsRepository flagsRepository) {
        this.bossBarService = bossBarService;
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.flagsRepository = flagsRepository;
    }

    @EventHandler
    void onPlayerBedLeave(PlayerBedLeaveEvent e){

        Player player = e.getPlayer();
        World world = player.getWorld();

        if(!ServerVersion.CURRENT_VERSION.supportsBossBars() || !this.flagsRepository.getUseBossBarFlag().getValueAt(world))
            return;

        Bukkit.getPluginManager().callEvent(new PlayerSleepStateChangeEvent(player, SleepState.AWAKE));

        //TODO move to PlayerSleepStateChangeEventListener
        int sleepingPlayersAmount = sleepService.getSleepersAmount(world);
        int playersRequiredAmount = Math.round(sleepService.getRequiredSleepersCount(world));

        if(this.sleepService.getSleepersAmount(world) == 0)
            this.bossBarService.setVisible(world, false);

        String configBossBarTitle = this.messageService.getConfigMessage(ConfigMessage.BOSS_BAR_TITLE);
        String bossBarTitle = new MessageBuilder(configBossBarTitle, "")
                .usePrefix(false)
                .setSleepingCount(sleepingPlayersAmount)
                .setSleepingRequiredCount(playersRequiredAmount)
                .build();

        BossBar bossBar = this.bossBarService.getBossBar(world);
        bossBar.setTitle(bossBarTitle);
        bossBar.setProgress(sleepService.getSleepersPercentage(world));
        //TODO END
    }
}
