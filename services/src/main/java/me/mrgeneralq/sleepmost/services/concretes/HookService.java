package me.mrgeneralq.sleepmost.services.concretes;

import me.mrgeneralq.sleepmost.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.interfaces.IHookRepository;
import me.mrgeneralq.sleepmost.interfaces.IHookService;
import me.mrgeneralq.sleepmost.models.Hook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Optional;

public class HookService implements IHookService {

    private final PluginManager pluginManager;
    private final IHookRepository hookRepository;

    public HookService(PluginManager pluginManager, IHookRepository hookRepository) {
        this.pluginManager = pluginManager;
        this.hookRepository = hookRepository;
    }

    @Override
    public void attemptRegister(Hook hook)  {

        if(!this.pluginManager.isPluginEnabled(hook.getName())){
            return;
        }

        for(Hook dependency: hook.getDependencies()){
            if(!this.pluginManager.isPluginEnabled(hook.getName())){
                Bukkit.getLogger().severe(String.format("[sleep-most] Missing required dependency {%s} for {%s}", dependency.getName(), hook.getName()));
                return;
            }
        }
        hook.setEnabled(true);
        this.hookRepository.addOrUpdate(hook);
        Bukkit.getLogger().info(String.format("[sleep-most] Hooked to %s" , hook.getName()));
    }

    @Override
    public Optional<Hook> getHook(SleepMostHook sleepMostHook) {

        Optional<Hook> hook = Optional.ofNullable(this.hookRepository.get(sleepMostHook));
        if(hook.isEmpty()){
            return Optional.empty();
        }

        if(!hook.get().isEnabled()){
            return Optional.empty();
        }
        return hook;
    }
}
