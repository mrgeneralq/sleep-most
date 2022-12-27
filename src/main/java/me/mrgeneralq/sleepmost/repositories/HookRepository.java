package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.enums.HookType;
import me.mrgeneralq.sleepmost.interfaces.IHookRepository;
import me.mrgeneralq.sleepmost.models.Hook;

import java.util.HashMap;
import java.util.Map;

public class HookRepository implements IHookRepository {

    private Map<HookType, Hook> hookMap = new HashMap<>();

    @Override
    public void addOrUpdate(Hook hook) {
        this.hookMap.put(hook.getType(), hook);
    }


    @Override
    public boolean exists(HookType hookType) {
        return this.hookMap.containsKey(hookType);
    }
}
