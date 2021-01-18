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
    private final PreventSleepFlag preventSleepFlag;
    private final PreventPhantomFlag preventPhantomFlag;
    private final NightcycleAnimationFlag nightcycleAnimationFlag;
    private final StormSleepFlag stormSleepFlag;
    private final UseAfkFlag useAfkFlag;
    private final CalculationMethodFlag calculationMethodFlag;
    private final PlayersRequiredFlag playersRequiredFlag;
    private final SkipDelayFlag skipDelayFlag;
    //private TestFlag testFlag;

    public FlagsRepository(IConfigRepository configRepository)
    {
        //setupFlag(this.testFlag = new TestFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.nightcycleAnimationFlag = new NightcycleAnimationFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.mobNoTargetFlag = new MobNoTargetFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.useExemptFlag = new UseExemptFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.preventSleepFlag = new PreventSleepFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.preventPhantomFlag = new PreventPhantomFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.stormSleepFlag = new StormSleepFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.useAfkFlag = new UseAfkFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.playersRequiredFlag = new PlayersRequiredFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.percentageRequiredFlag = new PercentageRequiredFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.calculationMethodFlag = new CalculationMethodFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.skipDelayFlag = new SkipDelayFlag(new ConfigFlagController<>(configRepository)));
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

    @Override
    public SkipDelayFlag getSkipDelayFlag() {
        return this.skipDelayFlag;
    }

    /*@Override public TestFlag getTestFlag(){
        return this.testFlag;
    }*/

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