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

    //TestFlag getTestFlag();
}
