package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.enums.HookType;
import me.mrgeneralq.sleepmost.interfaces.IHookRepository;
import me.mrgeneralq.sleepmost.interfaces.IHookService;
import me.mrgeneralq.sleepmost.models.Hook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

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
                Bukkit.getLogger().severe(String.format("Missing required dependency {%s} for {%s}", dependency.getName(), hook.getName()));
                return;
            }
        }
        this.hookRepository.addOrUpdate(hook);
        Bukkit.getLogger().info(String.format("Hooked to %s" , hook.getName()));
    }

    @Override
    public boolean isRegistered(HookType hookType) {
        return this.hookRepository.exists(hookType);
    }

}
