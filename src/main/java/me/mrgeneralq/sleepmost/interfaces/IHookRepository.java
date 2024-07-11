package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.models.Hook;

public interface IHookRepository {

    void addOrUpdate(Hook hook);
    boolean exists(SleepMostHook sleepMostHook);
    Hook get(SleepMostHook sleepMostHook);
}
