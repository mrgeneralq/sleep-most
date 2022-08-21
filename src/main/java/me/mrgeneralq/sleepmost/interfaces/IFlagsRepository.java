package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.flags.*;

import java.util.List;
import java.util.Set;

public interface IFlagsRepository
{
    ISleepFlag<?> getFlag(String flagName);
    boolean flagExists(String flagName);
    Set<ISleepFlag<?>> getFlags();
    List<String> getFlagsNames();

    //Flags Getters(easy accessibility + type safety)
    PercentageRequiredFlag getPercentageRequiredFlag();
    MobNoTargetFlag getMobNoTargetFlag();
    UseExemptFlag getUseExemptFlag();
    ExemptCreativeFlag getExemptCreativeFlag();
    ExemptSpectatorFlag getExemptSpectatorFlag();
    PreventSleepFlag getPreventSleepFlag();
    PreventPhantomFlag getPreventPhantomFlag();
    NightcycleAnimationFlag getNightcycleAnimationFlag();
    StormSleepFlag getStormSleepFlag();
    UseAfkFlag getUseAfkFlag();
    CalculationMethodFlag getCalculationMethodFlag();
    PlayersRequiredFlag getPlayersRequiredFlag();
    SkipDelayFlag getSkipDelayFlag();
    HealFlag getHealFlag();
    FeedFlag getFeedFlag();
    SkipStormFlag getSkipStormFlag();
    ExemptBelowYFlag getExemptBelowYFlag();
    ClockAnimationFlag getClockAnimationFlag();

    AllowSleepCmdFlag getSleepCmdFlag();

    UseBossBarFlag getUseBossBarFlag();

    UseSoundNightSkippedFlag getUseSoundNightSkippedFlag();
    UseSoundStormSkippedFlag getUseSoundStormSkippedFlag();
    UseTitleNightSkippedFlag getUseTitleNightSkippedFlag();
    UseTitleStormSkippedFlag getUseTitleStormSkippedFlag();

    SkipNightSoundFlag getSkipNightSoundFlag();
    SkipStormSoundFlag getSkipStormSoundFlag();

    //non-sleeping flags (all flags related to not showing stuff for non sleepers)
    NonSleepingClockAnimationFlag getNonSleepingClockAnimationFlag();
    NonSleepingSoundFlag getNonSleepingSoundFlag();
    NonSleepingTitleFlag getNonSleepingTitleFlag();

    ExemptFlyingFlag getExemptFlyingFlag();

    InsomniaChanceFlag getInsomniaChanceFlag();

    PhantomResetAudienceFlag getPhantomResetAudienceFlag();

    AllowKickFlag getAllowKickFlag();

    InsomniaMilkFlag getInsomniaMilkFlag();
}
