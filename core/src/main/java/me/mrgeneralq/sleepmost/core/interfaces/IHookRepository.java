package me.mrgeneralq.sleepmost.core.interfaces;

import me.mrgeneralq.sleepmost.core.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.core.models.Hook;

public interface IHookRepository {

    void addOrUpdate(Hook hook);
    boolean exists(SleepMostHook sleepMostHook);
    Hook get(SleepMostHook sleepMostHook);
}
