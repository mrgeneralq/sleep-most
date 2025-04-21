package me.mrgeneralq.sleepmost.services;
import me.mrgeneralq.sleepmost.models.hooks.Hook;
import me.mrgeneralq.sleepmost.models.hooks.SleepMostHook;

import java.util.Optional;

public interface IHookService {
    void attemptRegister(Hook hook);
    Optional<Hook> getHook(SleepMostHook sleepMostHook);
}
