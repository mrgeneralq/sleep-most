package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.enums.HookType;
import me.mrgeneralq.sleepmost.models.Hook;

public interface IHookRepository {

    void addOrUpdate(Hook hook);

    boolean exists(HookType hookType);
}
