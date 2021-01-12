package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.flags.*;
import me.mrgeneralq.sleepmost.flags.controllers.ConfigFlagController;
import me.mrgeneralq.sleepmost.flags.types.AbstractFlag;
import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class FlagsRepository implements IFlagsRepository
{
    private final Map<String, ISleepFlag<?>> flagByName = new HashMap<>();
    private final IConfigRepository configRepository;
    private final IConfigService configService;
    private final IMessageService messageService;

    //all flags objects(for type safety)
    private PercentageRequiredFlag percentageRequiredFlag;
    private MobNoTargetFlag mobNoTargetFlag;
    private UseExemptFlag useExemptFlag;
    private PreventSleepFlag preventSleepFlag;
    private PreventPhantomFlag preventPhantomFlag;
    private NightcycleAnimationFlag nightcycleAnimationFlag;
    private StormSleepFlag stormSleepFlag;
    private UseAfkFlag useAfkFlag;
    private CalculationMethodFlag calculationMethodFlag;
    private PlayersRequiredFlag playersRequiredFlag;

    public FlagsRepository(IConfigRepository configRepository, IConfigService configService, IMessageService messageService)
    {
        this.configRepository = configRepository;
        this.configService = configService;
        this.messageService = messageService;

        setup();
    }
    public void setup()
    {
        setupFlag(this.nightcycleAnimationFlag = new NightcycleAnimationFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.mobNoTargetFlag = new MobNoTargetFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.useExemptFlag = new UseExemptFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.preventSleepFlag = new PreventSleepFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.preventPhantomFlag = new PreventPhantomFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.stormSleepFlag = new StormSleepFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.useAfkFlag = new UseAfkFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.playersRequiredFlag = new PlayersRequiredFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.percentageRequiredFlag = new PercentageRequiredFlag(new ConfigFlagController<>(this.configRepository)));
        setupFlag(this.calculationMethodFlag = new CalculationMethodFlag(new ConfigFlagController<>(this.configRepository)));
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
                .collect(toList());
    }

    //Flags Getters
    public PercentageRequiredFlag getPercentageRequiredFlag(){
        return this.percentageRequiredFlag;
    }
    public MobNoTargetFlag getMobNoTargetFlag(){
        return this.mobNoTargetFlag;
    }
    public UseExemptFlag getUseExemptFlag() {
        return this.useExemptFlag;
    }
    public PreventSleepFlag getPreventSleepFlag() {
        return this.preventSleepFlag;
    }
    public PreventPhantomFlag getPreventPhantomFlag() {
        return this.preventPhantomFlag;
    }
    public NightcycleAnimationFlag getNightcycleAnimationFlag() {
        return this.nightcycleAnimationFlag;
    }
    public StormSleepFlag getStormSleepFlag() {
        return this.stormSleepFlag;
    }
    public UseAfkFlag getUseAfkFlag() {
        return this.useAfkFlag;
    }
    public CalculationMethodFlag getCalculationMethodFlag() {
        return this.calculationMethodFlag;
    }
    public PlayersRequiredFlag getPlayersRequiredFlag() {
        return this.playersRequiredFlag;
    }
    private <V> void setupFlag(ISleepFlag<V> flag)
    {
        //register the flag
        this.flagByName.put(flag.getName(), flag);

        //update the controller about which flag it controls
        if(flag instanceof AbstractFlag)
        {
            ((AbstractFlag<V>) flag).getController().setFlag(flag);
        }
    }
}