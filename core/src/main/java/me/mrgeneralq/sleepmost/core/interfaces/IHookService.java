package me.mrgeneralq.sleepmost.core.interfaces;
import me.mrgeneralq.sleepmost.core.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.core.models.Hook;

import java.util.Optional;

public interface IHookService {
    void attemptRegister(Hook hook);
    Optional<Hook> getHook(SleepMostHook sleepMostHook);
}
