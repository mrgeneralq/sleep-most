package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.flags.*;
import me.mrgeneralq.sleepmost.flags.controllers.ConfigFlagController;
import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.interfaces.IConfigService;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class SleepFlagRepository
{
    private final Map<String, ISleepFlag<?>> flagByName = new HashMap<>();

    private IConfigRepository configRepository;
    private IConfigService configService;
    private IMessageService messageService;

    //all flags objects, for easy accessibility and type safety
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

    public static SleepFlagRepository instance;
    private SleepFlagRepository(){}

    public void setup(IConfigRepository configRepository, IConfigService configService, IMessageService messageService){
        this.configRepository = configRepository;
        this.configService = configService;
        this.messageService = messageService;

        setupFlags();
        findWorldsWithIllegalValues().forEach(this::notifyAboutIllegalValues);
    }
    public static SleepFlagRepository getInstance(){
        if(instance == null)
            instance = new SleepFlagRepository();
        return instance;
    }
    public ISleepFlag<?> getFlag(String flagName){
        return flagByName.get(flagName);
    }
    public boolean flagExists(String flagName){
        return flagByName.containsKey(flagName);
    }
    public Set<ISleepFlag<?>> getFlags(){
        return new HashSet<>(this.flagByName.values());
    }
    public List<String> getFlagsNames(){
        return flagByName.values().stream()
                .map(ISleepFlag::getName)
                .collect(toList());
    }

    //Flags Getters
    public PercentageRequiredFlag getPercentageRequiredFlag(){ return percentageRequiredFlag; }
    public MobNoTargetFlag getMobNoTargetFlag(){ return mobNoTargetFlag; }
    public UseExemptFlag getUseExemptFlag(){ return useExemptFlag; }
    public PreventSleepFlag getPreventSleepFlag(){ return preventSleepFlag; }
    public PreventPhantomFlag getPreventPhantomFlag(){ return preventPhantomFlag; }
    public NightcycleAnimationFlag getNightcycleAnimationFlag(){ return nightcycleAnimationFlag; }
    public StormSleepFlag getStormSleepFlag(){ return stormSleepFlag; }
    public UseAfkFlag getUseAfkFlag(){ return useAfkFlag; }
    public CalculationMethodFlag getCalculationMethodFlag(){ return calculationMethodFlag; }
    public PlayersRequiredFlag getPlayersRequiredFlag(){ return playersRequiredFlag; }

    private void setupFlags(){
        setupFlag(this.percentageRequiredFlag = new PercentageRequiredFlag());
        setupFlag(this.mobNoTargetFlag = new MobNoTargetFlag());
        setupFlag(this.useExemptFlag = new UseExemptFlag());
        setupFlag(this.preventSleepFlag = new PreventSleepFlag());
        setupFlag(this.preventPhantomFlag = new PreventPhantomFlag());
        setupFlag(this.nightcycleAnimationFlag = new NightcycleAnimationFlag());
        setupFlag(this.stormSleepFlag = new StormSleepFlag());
        setupFlag(this.useAfkFlag = new UseAfkFlag());
        setupFlag(this.calculationMethodFlag = new CalculationMethodFlag());
        setupFlag(this.playersRequiredFlag = new PlayersRequiredFlag());
    }
    private <V> void setupFlag(ISleepFlag<V> flag){
        this.flagByName.put(flag.getName(), flag);

        ConfigFlagController<V> controller = new ConfigFlagController<>(this.configRepository);
        flag.setController(controller);
        controller.setFlag(flag);
    }
    private void notifyAboutIllegalValues(World world, Map<ISleepFlag<?>, String> illegalValues){
        illegalValues.forEach((flag, value) ->
        {
            Bukkit.getConsoleSender().sendMessage(messageService.newPrefixedBuilder("&cThe value of the &e%flagName &cflag at &b%worldName &cis &4illegal(&c%value&4)")
                    .setPlaceHolder("%flagName", flag.getName())
                    .setPlaceHolder("%value", value)
                    .setPlaceHolder("%worldName", world.getName())
                    .build());
        });

        //TODO: Implement this defensive mechanism
        Bukkit.getConsoleSender().sendMessage(messageService.newPrefixedBuilder("&4Therefore the world will not be used.").build());
    }
    private Map<World, Map<ISleepFlag<?>, String>> findWorldsWithIllegalValues()
    {
        return this.configService.getActivatedWorlds().stream()
                .map(world -> new SimpleEntry<>(world, findIllegalValues(world)))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(toMap(Entry::getKey, Entry::getValue));
    }
    private Map<ISleepFlag<?>, String> findIllegalValues(World world)
    {
        return this.flagByName.values().stream()
                .map(flag -> new SimpleEntry<>(flag, configRepository.getFlagValue(world, flag)))
                .filter(entry -> !entry.getKey().isValidValue(entry.getValue()))
                .collect(toMap(Entry::getKey, Entry::getValue));
    }
}