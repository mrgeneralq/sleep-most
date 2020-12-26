package me.mrgeneralq.sleepmost.statics;

import me.mrgeneralq.sleepmost.flags.*;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SleepFlagMapper {

    private ISleepFlagService sleepFlagService;

    public static SleepFlagMapper mapper;
    private HashMap<String, ISleepFlag> allFlags = new HashMap<>();

    private SleepFlagMapper() { }

    public void setup(ISleepFlagService sleepFlagService){
        this.sleepFlagService = sleepFlagService;
        this.setupFlags();

    }

    private void setupFlags(){
        allFlags.put("percentage-required", new PercentageRequiredFlag(this.sleepFlagService));
        allFlags.put("mob-no-target", new MobNoTargetFlag(this.sleepFlagService));
        allFlags.put("use-exempt", new UseExemptFlag(this.sleepFlagService));
        allFlags.put("prevent-sleep", new PreventSleepFlag(this.sleepFlagService));
        allFlags.put("prevent-phantom", new PreventPhantomFlag(this.sleepFlagService));
        allFlags.put("nightcycle-animation", new NightcycleAnimationFlag(this.sleepFlagService));
        allFlags.put("storm-sleep", new StormSleepFlag(this.sleepFlagService));
        allFlags.put("use-afk", new UseAfkFlag(this.sleepFlagService));
        allFlags.put("calculation-method", new CalculationMethodFlag(this.sleepFlagService));
        allFlags.put("players-required", new PlayersRequiredFlag(this.sleepFlagService));
    }

    public static SleepFlagMapper getMapper(){
        if(mapper == null)
            mapper = new SleepFlagMapper();
        return mapper;
    }

    public ISleepFlag<?> getFlag(String flag){
        return allFlags.get(flag);
    }

    public boolean flagExists(String flag){
        return allFlags.containsKey(flag);
    }

    public List<String> getAllFlags(){
        return new ArrayList<>(this.allFlags.keySet());
    }
}
