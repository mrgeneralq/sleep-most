package me.mrgeneralq.sleepmost.services;
import me.mrgeneralq.sleepmost.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.models.Hook;

import java.util.Optional;

public interface IHookService {
    void attemptRegister(Hook hook);
    Optional<Hook> getHook(SleepMostHook sleepMostHook);
}
