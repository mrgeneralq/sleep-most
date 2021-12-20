package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.flags.*;
import me.mrgeneralq.sleepmost.flags.controllers.ConfigFlagController;
import me.mrgeneralq.sleepmost.flags.types.AbstractFlag;
import me.mrgeneralq.sleepmost.interfaces.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class FlagsRepository implements IFlagsRepository
{
    private final Map<String, ISleepFlag<?>> flagByName = new HashMap<>();

    //all flags objects(for type safety)
    private final PercentageRequiredFlag percentageRequiredFlag;
    private final MobNoTargetFlag mobNoTargetFlag;
    private final UseExemptFlag useExemptFlag;
    private final ExemptCreativeFlag exemptCreativeFlag;
    private final ExemptSpectatorFlag exemptSpectatorFlag;
    private final PreventSleepFlag preventSleepFlag;
    private final PreventPhantomFlag preventPhantomFlag;
    private final NightcycleAnimationFlag nightcycleAnimationFlag;
    private final StormSleepFlag stormSleepFlag;
    private final UseAfkFlag useAfkFlag;
    private final CalculationMethodFlag calculationMethodFlag;
    private final PlayersRequiredFlag playersRequiredFlag;
    private final SkipDelayFlag skipDelayFlag;
    private final HealFlag healFlag;
    private final FeedFlag feedFlag;
    private final ExemptBelowYFlag exemptBelowYFlag;
    private final SkipStormFlag skipStormFlag;
    private final ClockAnimationFlag clockAnimationFlag;
    private final AllowSleepCmdFlag sleepCmdFlag;
    private final UseBossBarFlag useBossBarFlag;

    public FlagsRepository(IConfigRepository configRepository)
    {
        setupFlag(this.nightcycleAnimationFlag = new NightcycleAnimationFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.mobNoTargetFlag = new MobNoTargetFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.useExemptFlag = new UseExemptFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.exemptCreativeFlag = new ExemptCreativeFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.exemptSpectatorFlag = new ExemptSpectatorFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.preventSleepFlag = new PreventSleepFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.preventPhantomFlag = new PreventPhantomFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.stormSleepFlag = new StormSleepFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.useAfkFlag = new UseAfkFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.playersRequiredFlag = new PlayersRequiredFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.percentageRequiredFlag = new PercentageRequiredFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.calculationMethodFlag = new CalculationMethodFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.skipDelayFlag = new SkipDelayFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.healFlag = new HealFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.feedFlag = new FeedFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.exemptBelowYFlag = new ExemptBelowYFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.skipStormFlag = new SkipStormFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.clockAnimationFlag = new ClockAnimationFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.sleepCmdFlag = new AllowSleepCmdFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.useBossBarFlag = new UseBossBarFlag(new ConfigFlagController<>(configRepository)));
    }

    @Override
    public ISleepFlag<?> getFlag(String flagName)
    {
        return this.flagByName.get(flagName);
    }

    @Override
    public boolean flagExists(String flagName)
    {
        return this.flagByName.containsKey(flagName);
    }

    @Override
    public Set<ISleepFlag<?>> getFlags()
    {
        return new HashSet<>(this.flagByName.values());
    }

    @Override
    public List<String> getFlagsNames(){
        return flagByName.values().stream()
                .map(ISleepFlag::getName)
                .sorted()
                .collect(toList());
    }

    //Flags Getters
    @Override public PercentageRequiredFlag getPercentageRequiredFlag(){
        return this.percentageRequiredFlag;
    }
    @Override public MobNoTargetFlag getMobNoTargetFlag(){
        return this.mobNoTargetFlag;
    }
    @Override public UseExemptFlag getUseExemptFlag() {
        return this.useExemptFlag;
    }

    @Override
    public ExemptCreativeFlag getExemptCreativeFlag() {
        return this.exemptCreativeFlag;
    }

    @Override
    public ExemptSpectatorFlag getExemptSpectatorFlag() {
        return this.exemptSpectatorFlag;
    }

    @Override public PreventSleepFlag getPreventSleepFlag() {
        return this.preventSleepFlag;
    }
    @Override public PreventPhantomFlag getPreventPhantomFlag() {
        return this.preventPhantomFlag;
    }
    @Override public NightcycleAnimationFlag getNightcycleAnimationFlag() {
        return this.nightcycleAnimationFlag;
    }
    @Override public StormSleepFlag getStormSleepFlag() {
        return this.stormSleepFlag;
    }
    @Override public UseAfkFlag getUseAfkFlag() {
        return this.useAfkFlag;
    }
    @Override public CalculationMethodFlag getCalculationMethodFlag() {
        return this.calculationMethodFlag;
    }
    @Override public PlayersRequiredFlag getPlayersRequiredFlag() {
        return this.playersRequiredFlag;
    }
    @Override public SkipDelayFlag getSkipDelayFlag() {
        return this.skipDelayFlag;
    }
    @Override public HealFlag getHealFlag() {
        return this.healFlag;
    }
    @Override public FeedFlag getFeedFlag() {
        return this.feedFlag;
    }
    @Override public ExemptBelowYFlag getExemptBelowYFlag(){ return this.exemptBelowYFlag; }
    @Override public SkipStormFlag getSkipStormFlag() {
        return this.skipStormFlag;
    }
    @Override public ClockAnimationFlag getClockAnimationFlag() {
        return this.clockAnimationFlag;
    }
    @Override public AllowSleepCmdFlag getSleepCmdFlag() {
        return this.sleepCmdFlag;
    }
    @Override public UseBossBarFlag getUseBossBarFlag() {
        return useBossBarFlag;
    }

    private <V> void setupFlag(ISleepFlag<V> flag) {
        //register the flag
        this.flagByName.put(flag.getName(), flag);

        //update the controller about which flag it controls
        if(flag instanceof AbstractFlag)
        {
            ((AbstractFlag<V>) flag).getController().setFlag(flag);
        }
    }
}