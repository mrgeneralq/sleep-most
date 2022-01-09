package me.mrgeneralq.sleepmost.eventlisteners;

import static me.mrgeneralq.sleepmost.enums.SleepSkipCause.NIGHT_TIME;

import me.mrgeneralq.sleepmost.exceptions.InvalidSleepSkipCauseOccurredException;
import me.mrgeneralq.sleepmost.flags.UseSkipSoundFlag;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import me.mrgeneralq.sleepmost.statics.DataContainer;

import java.util.List;

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

        if (dataContainer.isAnimationRunningAt(world))
            return;

        resetPhantomCounter(world);
        sendSkipSound(world, e.getCause());

        if (ServerVersion.CURRENT_VERSION.supportsTitles())
            sendSkipTitle(world, e.getCause());

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

    private void resetPhantomCounter(World world) {
        /*
         * DISCLAIMER: Statistic and TIME_SINCE_REST Does not exist
         * in older versions of Minecraft
         */
        try {
            for (Player p : world.getPlayers())
                p.setStatistic(Statistic.TIME_SINCE_REST, 0);
        } catch (NoSuchFieldError error) {
            // statistic did not exist yet in some versions
        }
    }

    private void sendSkipTitle(World world, SleepSkipCause cause) {

        boolean titleEnabled = (cause == NIGHT_TIME ? this.flagsRepository.getUseTitleNightSkippedFlag().getValueAt(world) :
                this.flagsRepository.getUseTitleStormSkippedFlag().getValueAt(world));

        if (!titleEnabled) {
            return;
        }
        String skippedTitle = (cause == NIGHT_TIME ? configService.getTitleNightSkippedTitle() : configService.getTitleStormSkippedTitle());
        String skippedSubtitle = (cause == NIGHT_TIME ? configService.getTitleNightSkippedSubTitle() : configService.getTitleStormSkippedSubTitle());

        for (Player p : world.getPlayers()) {
            String playerTitle = skippedTitle = skippedTitle.replaceAll("%player%", p.getName()).replaceAll("%dplayer%", p.getDisplayName());
            String playerSubtitle = skippedSubtitle.replaceAll("%player%", p.getName()).replaceAll("%dplayer%", p.getDisplayName());

            p.sendTitle(playerTitle, playerSubtitle, 10, 70, 20);
        }
    }

    private void sendSkipSound(World world, SleepSkipCause cause) throws InvalidSleepSkipCauseOccurredException {
        if (this.getSkipSoundEnabledFlag(cause).getValueAt(world)) return;

        for (Player p : world.getPlayers()) {
            p.playSound(p.getLocation(), this.getSkipSound(cause), 0.4F, 1F);
        }
    }

    /**
     * Get the appropriate sound enabled flag for this cause.
     *
     * @param cause Cause of skip event.
     *
     * @return UseSkipSoundFlag for this cause. Defaults to flag with false for default.
     */
    private UseSkipSoundFlag getSkipSoundEnabledFlag(SleepSkipCause cause) {
        switch (cause) {
            case NIGHT_TIME:
                return flagsRepository.getUseSoundNightSkippedFlag();
            case STORM:
                return flagsRepository.getUseSoundStormSkippedFlag();
            default:
                return new UseSkipSoundFlag("HARDCODED_FLAG", false, null);
        }
    }

    /**
     * Get the skip sound Minecraft resource key.
     *
     * @param cause Cause of skip event.
     * @return Resource key for the sound used.
     * @throws InvalidSleepSkipCauseOccurredException because no valid cause were found.
     */
    private String getSkipSound(SleepSkipCause cause) throws InvalidSleepSkipCauseOccurredException {
        switch (cause) {
            case NIGHT_TIME:
                return configService.getNightSkippedSound();
            case STORM:
                return configService.getStormSkippedSound();
            default:
                break;
        }

        throw new InvalidSleepSkipCauseOccurredException("");
    }
}