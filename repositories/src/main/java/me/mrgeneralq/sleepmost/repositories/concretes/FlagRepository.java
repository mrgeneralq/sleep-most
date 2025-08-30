package me.mrgeneralq.sleepmost.repositories.concretes;

import me.mrgeneralq.sleepmost.repositories.IFlagsRepository;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class FlagRepository implements IFlagsRepository {


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
    private final UseSoundNightSkippedFlag useSoundNightSkippedFlag;
    private final UseSoundStormSkippedFlag useSoundStormSkippedFlag;
    private final UseTitleNightSkippedFlag useTitleNightSkippedFlag;
    private final UseTitleStormSkippedFlag useTitleStormSkippedFlag;
    private final SkipNightSoundFlag skipNightSoundFlag;
    private final SkipStormSoundFlag skipStormSoundFlag;
    private final NonSleepingClockAnimationFlag nonSleepingClockAnimationFlag;
    private final NonSleepingSoundFlag nonSleepingSoundFlag;
    private final NonSleepingTitleFlag nonSleepingTitleFlag;
    private final ExemptFlyingFlag exemptFlyingFlag;
    private final InsomniaChanceFlag insomniaChanceFlag;
    private final PhantomResetAudienceFlag phantomResetAudienceFlag;
    private final AllowKickFlag allowKickFlag;
    private final InsomniaMilkFlag insomniaMilkFlag;
    private final DynamicAnimationSpeedFlag dynamicAnimationSpeedFlag;
    private final IHookService hookService;
    private LongerNightDurationFlag longerNightDurationFlag;
    private final ResetTimeSinceRestFlag resetTimeSinceRestFlag;
    private DisableDaylightcycleGamerule disableDaylightcycleGamerule;
    private ForceNightcycleAnimationFlag forceNightcycleAnimationFlag;
    private SkipMsgAudienceFlag skipMsgAudienceFlag;



    //DEPENDING ON HOOK
    private GSitHookFlag gSitHookFlag;
    private GSitSleepFlag gSitSleepFlag;
    private GsitSleepCmdFlag gsitSleepCmdFlag;


    public FlagRepository(IHookService hookService , IConfigRepository configRepository) {
        this.hookService = hookService;

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
        setupFlag(this.useTitleStormSkippedFlag = new UseTitleStormSkippedFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.useTitleNightSkippedFlag = new UseTitleNightSkippedFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.useSoundNightSkippedFlag = new UseSoundNightSkippedFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.useSoundStormSkippedFlag = new UseSoundStormSkippedFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.skipNightSoundFlag = new SkipNightSoundFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.skipStormSoundFlag = new SkipStormSoundFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.nonSleepingClockAnimationFlag = new NonSleepingClockAnimationFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.nonSleepingSoundFlag = new NonSleepingSoundFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.nonSleepingTitleFlag = new NonSleepingTitleFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.exemptFlyingFlag = new ExemptFlyingFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.insomniaChanceFlag = new InsomniaChanceFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.phantomResetAudienceFlag = new PhantomResetAudienceFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.allowKickFlag = new AllowKickFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.insomniaMilkFlag = new InsomniaMilkFlag(new ConfigFlagController<>(configRepository)));

        setupFlag(this.dynamicAnimationSpeedFlag = new DynamicAnimationSpeedFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.disableDaylightcycleGamerule = new DisableDaylightcycleGamerule(new ConfigFlagController<>(configRepository)));

        if(ServerVersion.CURRENT_VERSION.supportsGameRules()){
            setupFlag(this.longerNightDurationFlag = new LongerNightDurationFlag(new ConfigFlagController<>(configRepository)));
            setupFlag(this.disableDaylightcycleGamerule = new DisableDaylightcycleGamerule(new ConfigFlagController<>(configRepository)));
        }

        //GSIT
        setupFlag(this.gSitHookFlag = new GSitHookFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.gSitSleepFlag = new GSitSleepFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.gsitSleepCmdFlag = new GsitSleepCmdFlag(new ConfigFlagController<>(configRepository)));


        setupFlag(this.resetTimeSinceRestFlag = new ResetTimeSinceRestFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.forceNightcycleAnimationFlag = new ForceNightcycleAnimationFlag(new ConfigFlagController<>(configRepository)));
        setupFlag(this.skipMsgAudienceFlag = new SkipMsgAudienceFlag(new ConfigFlagController<>(configRepository)));

    }

    @Override
    public ISleepFlag<?> getFlag(String flagName) {
        return this.flagByName.get(flagName);
    }

    @Override
    public boolean flagExists(String flagName) {
        return this.flagByName.containsKey(flagName);
    }

    @Override
    public Set<ISleepFlag<?>> getFlags() {
        return new HashSet<>(this.flagByName.values());
    }

    @Override
    public List<String> getFlagsNames() {
        return flagByName.values().stream()
                .map(ISleepFlag::getName)
                .sorted()
                .collect(toList());
    }

    //Flags Getters
    @Override
    public PercentageRequiredFlag getPercentageRequiredFlag() {
        return this.percentageRequiredFlag;
    }

    @Override
    public MobNoTargetFlag getMobNoTargetFlag() {
        return this.mobNoTargetFlag;
    }

    @Override
    public UseExemptFlag getUseExemptFlag() {
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

    @Override
    public PreventSleepFlag getPreventSleepFlag() {
        return this.preventSleepFlag;
    }

    @Override
    public PreventPhantomFlag getPreventPhantomFlag() {
        return this.preventPhantomFlag;
    }

    @Override
    public NightcycleAnimationFlag getNightcycleAnimationFlag() {
        return this.nightcycleAnimationFlag;
    }

    @Override
    public StormSleepFlag getStormSleepFlag() {
        return this.stormSleepFlag;
    }

    @Override
    public UseAfkFlag getUseAfkFlag() {
        return this.useAfkFlag;
    }

    @Override
    public CalculationMethodFlag getCalculationMethodFlag() {
        return this.calculationMethodFlag;
    }

    @Override
    public PlayersRequiredFlag getPlayersRequiredFlag() {
        return this.playersRequiredFlag;
    }

    @Override
    public SkipDelayFlag getSkipDelayFlag() {
        return this.skipDelayFlag;
    }

    @Override
    public HealFlag getHealFlag() {
        return this.healFlag;
    }

    @Override
    public FeedFlag getFeedFlag() {
        return this.feedFlag;
    }

    @Override
    public ExemptBelowYFlag getExemptBelowYFlag() {
        return this.exemptBelowYFlag;
    }

    @Override
    public SkipStormFlag getSkipStormFlag() {
        return this.skipStormFlag;
    }

    @Override
    public ClockAnimationFlag getClockAnimationFlag() {
        return this.clockAnimationFlag;
    }

    @Override
    public AllowSleepCmdFlag getSleepCmdFlag() {
        return this.sleepCmdFlag;
    }

    @Override
    public UseBossBarFlag getUseBossBarFlag() {
        return useBossBarFlag;
    }

    @Override
    public UseSoundNightSkippedFlag getUseSoundNightSkippedFlag() {
        return useSoundNightSkippedFlag;
    }

    @Override
    public UseSoundStormSkippedFlag getUseSoundStormSkippedFlag() {
        return useSoundStormSkippedFlag;
    }

    @Override
    public UseTitleNightSkippedFlag getUseTitleNightSkippedFlag() {
        return useTitleNightSkippedFlag;
    }

    @Override
    public UseTitleStormSkippedFlag getUseTitleStormSkippedFlag() {
        return useTitleStormSkippedFlag;
    }

    @Override
    public SkipNightSoundFlag getSkipNightSoundFlag() {
        return skipNightSoundFlag;
    }

    @Override
    public SkipStormSoundFlag getSkipStormSoundFlag() {
        return skipStormSoundFlag;
    }

    @Override
    public NonSleepingClockAnimationFlag getNonSleepingClockAnimationFlag() {
        return nonSleepingClockAnimationFlag;
    }

    @Override
    public NonSleepingSoundFlag getNonSleepingSoundFlag() {
        return nonSleepingSoundFlag;
    }

    @Override
    public NonSleepingTitleFlag getNonSleepingTitleFlag() {
        return nonSleepingTitleFlag;
    }

    @Override
    public ExemptFlyingFlag getExemptFlyingFlag() {
        return exemptFlyingFlag;
    }

    @Override
    public InsomniaChanceFlag getInsomniaChanceFlag() {
        return insomniaChanceFlag;
    }

    @Override
    public PhantomResetAudienceFlag getPhantomResetAudienceFlag() {
        return phantomResetAudienceFlag;
    }

    @Override
    public AllowKickFlag getAllowKickFlag() {
        return allowKickFlag;
    }

    @Override
    public InsomniaMilkFlag getInsomniaMilkFlag() {
        return insomniaMilkFlag;
    }

    @Override
    public GSitHookFlag getGSitHookFlag() {
        return gSitHookFlag;
    }

    @Override
    public GSitSleepFlag getGSitSleepFlag() {
        return gSitSleepFlag;
    }

    @Override
    public DynamicAnimationSpeedFlag getDynamicAnimationSpeedFlag() {
        return dynamicAnimationSpeedFlag;
    }

    @Override
    public LongerNightDurationFlag getLongerNightDurationFlag() {
        return longerNightDurationFlag;
    }

    @Override
    public ResetTimeSinceRestFlag getResetTimeSinceRestFlag() {
        return resetTimeSinceRestFlag;
    }

    @Override
    public DisableDaylightcycleGamerule getDisableDaylightcycleGamerule() {
        return disableDaylightcycleGamerule;
    }

    @Override
    public ForceNightcycleAnimationFlag getForceNightcycleAnimationFlag() {
        return forceNightcycleAnimationFlag;
    }

    @Override
    public SkipMsgAudienceFlag getSkipMsgAudienceFlag() {
        return skipMsgAudienceFlag;
    }

    @Override
    public GsitSleepCmdFlag getGsitSleepCmdFlag() {
        return gsitSleepCmdFlag;
    }

    private <V> void setupFlag(ISleepFlag<V> flag) {
        //register the flag
        this.flagByName.put(flag.getName(), flag);

        //update the controller about which flag it controls
        if (flag instanceof AbstractFlag) {
            ((AbstractFlag<V>) flag).getController().setFlag(flag);
        }
    }
}