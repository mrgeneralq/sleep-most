package me.mrgeneralq.sleepmost.mappers;

import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.enums.SleepmostFlag;
import me.mrgeneralq.sleepmost.flags.*;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SleepFlagMapper {

    private ISleepFlagService sleepFlagService;

    public static SleepFlagMapper mapper;
    private Map<SleepmostFlag, ISleepFlag> allFlags = new HashMap<>();

    private SleepFlagMapper() { }

    public void setup(ISleepFlagService sleepFlagService){
        this.sleepFlagService = sleepFlagService;
        this.setupFlags();

    }

    private void setupFlags(){
        allFlags.put(SleepmostFlag.PERCENTAGE_REQUIRED, new PercentageRequiredFlag(this.sleepFlagService));
        allFlags.put(SleepmostFlag.MOB_NO_TARGET, new MobNoTargetFlag(this.sleepFlagService));
        allFlags.put(SleepmostFlag.USE_EXEMPT, new UseExemptFlag(this.sleepFlagService));
        allFlags.put(SleepmostFlag.PREVENT_SLEEP, new PreventSleepFlag(this.sleepFlagService));
        allFlags.put(SleepmostFlag.PREVENT_PHANTOM, new PreventPhantomFlag(this.sleepFlagService));
        allFlags.put(SleepmostFlag.NIGHTCYCLE_ANIMATION, new NightcycleAnimationFlag(this.sleepFlagService));
        allFlags.put(SleepmostFlag.STORM_SLEEP, new StormSleepFlag(this.sleepFlagService));
        allFlags.put(SleepmostFlag.USE_AFK, new UseAfkFlag(this.sleepFlagService));
        allFlags.put(SleepmostFlag.CALCULATION_METHOD, new CalculationMethodFlag(this.sleepFlagService));
        allFlags.put(SleepmostFlag.PLAYERS_REQUIRED, new PlayersRequiredFlag(this.sleepFlagService));
    }

    public static SleepFlagMapper getMapper(){
        if(mapper == null)
            mapper = new SleepFlagMapper();
        return mapper;
    }

    public ISleepFlag<?> getFlag(SleepmostFlag flag){
        return allFlags.get(flag);
    }

    public boolean flagExists(SleepmostFlag flag){
        return allFlags.containsKey(flag);
    }

    public List<String> getAllFlags(){
        return allFlags.values().stream().map(ISleepFlag::getFlagName).collect(Collectors.toList());
    }
}
