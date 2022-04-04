package me.mrgeneralq.sleepmost.eventlisteners;

import static me.mrgeneralq.sleepmost.enums.SleepSkipCause.NIGHT_TIME;
import me.mrgeneralq.sleepmost.exceptions.InvalidSleepSkipCauseOccurredException;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.flags.SkipSoundFlag;
import me.mrgeneralq.sleepmost.flags.UseSkipSoundFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.messages.MessageBuilder;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import me.mrgeneralq.sleepmost.statics.DataContainer;

import java.util.List;
import java.util.stream.Collectors;

public class SleepSkipEventListener implements Listener {

    private final IMessageService messageService;
    private final IConfigService configService;
    private final ISleepService sleepService;
    private final IFlagsRepository flagsRepository;
    private final IBossBarService bossBarService;
    private final DataContainer dataContainer = DataContainer.getContainer();

    public SleepSkipEventListener(IMessageService messageService,
                                  IConfigService configService,
                                  ISleepService sleepService,
                                  IFlagsRepository flagsRepository,
                                  IBossBarService bossBarService
    ) {

        this.messageService = messageService;
        this.configService = configService;
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.bossBarService = bossBarService;

    }

    @EventHandler
    public void onSleepSkip(SleepSkipEvent e) throws InvalidSleepSkipCauseOccurredException {

        World world = e.getWorld();
        List<OfflinePlayer> sleepers = e.getPeopleWhoSlept();

        if (dataContainer.isAnimationRunningAt(world))
            return;

        resetPhantomCounter(world, sleepers);
        sendSkipSound(world, e);

        if (ServerVersion.CURRENT_VERSION.supportsTitles()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendSkipTitle(world, e);
                }
            }.runTaskLater(Sleepmost.getInstance(), 5);
        }
        
        boolean shouldHeal = flagsRepository.getHealFlag().getValueAt(world);
        boolean shouldFeed = flagsRepository.getFeedFlag().getValueAt(world);

        List<OfflinePlayer> sleepingPlayers = e.getPeopleWhoSlept();

        sleepingPlayers.forEach(p -> {

            if(p.isOnline()){
                if (shouldHeal)
                    ServerVersion.CURRENT_VERSION.healToMaxHP(p.getPlayer());

                if (shouldFeed)
                    p.getPlayer().setFoodLevel(20);
            }

        });
        this.messageService.sendNightSkippedMessage(e.getWorld(), e.getLastSleeperName(), e.getLastSleeperDisplayName(), e.getCause());
        this.sleepService.clearSleepersAt(world);
        this.bossBarService.setVisible(world, false);
    }

    private void resetPhantomCounter(World world, List<OfflinePlayer> playersWhoSlept) {
        /*
         * DISCLAIMER: Statistic and TIME_SINCE_REST Does not exist
         * in older versions of Minecraft
         */
        try {
            for (Player p : playersWhoSlept.stream().filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList()))
                p.setStatistic(Statistic.TIME_SINCE_REST, 0);
        } catch (NoSuchFieldError error) {
            // statistic did not exist yet in some versions
        }
    }

    private void sendSkipTitle(World world , SleepSkipEvent e) {

        SleepSkipCause cause = e.getCause();

        boolean titleEnabled = (cause == NIGHT_TIME ? this.flagsRepository.getUseTitleNightSkippedFlag().getValueAt(world) :
                this.flagsRepository.getUseTitleStormSkippedFlag().getValueAt(world));

        if (!titleEnabled) {
            return;
        }
        String skippedTitle = (cause == NIGHT_TIME ? configService.getTitleNightSkippedTitle() : configService.getTitleStormSkippedTitle());
        String skippedSubtitle = (cause == NIGHT_TIME ? configService.getTitleNightSkippedSubTitle() : configService.getTitleStormSkippedSubTitle());

        List<Player> playerList = (flagsRepository.getNonSleepingTitleFlag().getValueAt(world) ?
                world.getPlayers():
                e.getPeopleWhoSlept().stream().filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList()));

        for (Player p : playerList) {

            MessageBuilder titleMessageBuilder = new MessageBuilder(skippedTitle, "")
                    .setPlayer(p)
                    .usePrefix(false)
                    .setWorld(world);

            String playerTitle = titleMessageBuilder.build();
            titleMessageBuilder.setMessage(skippedSubtitle);
            String playerSubTitle = titleMessageBuilder.build();

            p.sendTitle(playerTitle, playerSubTitle, 10, 70, 20);
        }
    }

    private void sendSkipSound(World world, SleepSkipEvent e) throws InvalidSleepSkipCauseOccurredException {
        SleepSkipCause cause = e.getCause();
        if (!this.getSkipSoundEnabledFlag(cause).getValueAt(world)) return;

        String skipSound = this.getSkipSoundFlag(cause).getValueAt(world);

        List<Player> playerList = (flagsRepository.getNonSleepingSoundFlag().getValueAt(world) ?
                world.getPlayers():
                e.getPeopleWhoSlept().stream().filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList()));

        for (Player p : playerList) {
            p.playSound(p.getLocation(), skipSound, 0.4F, 1F);
        }
    }

    /**
     * Get the appropriate sound enabled flag for this cause.
     * @param cause Cause of skip event.
     * @return UseSkipSoundFlag for this cause. Defaults to flag with false for default.
     */
    private UseSkipSoundFlag getSkipSoundEnabledFlag(SleepSkipCause cause) {
        switch (cause) {
            case NIGHT_TIME:
                return flagsRepository.getUseSoundNightSkippedFlag();
            case STORM:
                return flagsRepository.getUseSoundStormSkippedFlag();
            default:
                return new UseSkipSoundFlag("HARDCODED_FLAG", false, null) {};
        }
    }

    /**
     * Get the skip sound Minecraft resource key.
     *
     * @param cause Cause of skip event.
     * @return Resource key for the sound used.
     * @throws InvalidSleepSkipCauseOccurredException because no valid cause were found.
     */
    private SkipSoundFlag getSkipSoundFlag(SleepSkipCause cause) throws InvalidSleepSkipCauseOccurredException {
        switch (cause) {
            case NIGHT_TIME:
                return flagsRepository.getSkipNightSoundFlag();
            case STORM:
                return flagsRepository.getSkipStormSoundFlag();
            default:
                throw new InvalidSleepSkipCauseOccurredException("Skip was not caused by storm or night.");
        }
    }
}
