package me.mrgeneralq.sleepmost.repositories.concretes;

import me.mrgeneralq.sleepmost.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.interfaces.IHookRepository;
import me.mrgeneralq.sleepmost.models.Hook;

import java.util.HashMap;
import java.util.Map;

public class HookRepository implements IHookRepository {

    private Map<SleepMostHook, Hook> hookMap = new HashMap<>();

    @Override
    public void addOrUpdate(Hook hook) {
        this.hookMap.put(hook.getType(), hook);
    }


    @Override
    public boolean exists(SleepMostHook sleepMostHook) {
        return this.hookMap.containsKey(sleepMostHook);
    }

    @Override
    public Hook get(SleepMostHook sleepMostHook) {
        return hookMap.getOrDefault(sleepMostHook, null);
    }
}
