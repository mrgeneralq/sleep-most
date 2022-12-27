package me.mrgeneralq.sleepmost.interfaces;
import me.mrgeneralq.sleepmost.enums.HookType;
import me.mrgeneralq.sleepmost.models.Hook;

public interface IHookService {
    void attemptRegister(Hook hook);
    boolean isRegistered(HookType hookType);
}
