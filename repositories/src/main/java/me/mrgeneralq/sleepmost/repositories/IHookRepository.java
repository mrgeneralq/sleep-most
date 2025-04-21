package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.models.Hook;

public interface IHookRepository {

    void addOrUpdate(Hook hook);
    boolean exists(SleepMostHook sleepMostHook);
    Hook get(SleepMostHook sleepMostHook);
}
