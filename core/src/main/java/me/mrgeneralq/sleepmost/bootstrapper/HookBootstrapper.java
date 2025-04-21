package me.mrgeneralq.sleepmost.bootstrapper;

import com.google.inject.Inject;
import me.mrgeneralq.sleepmost.models.hooks.*;
import me.mrgeneralq.sleepmost.services.IHookService;

public class HookBootstrapper {

    private final IHookService hookService;

    @Inject
    public HookBootstrapper(IHookService hookService) {
        this.hookService = hookService;
    }


    public void bootstrap() {

        Hook superVanishHook = new SuperVanishHook();
        superVanishHook.addAlias("PremiumVanish");

        hookService.attemptRegister(superVanishHook);
        hookService.attemptRegister(new PlaceholderAPIHook());
        hookService.attemptRegister(new GsitHook());
        hookService.attemptRegister(new EssentialsHook());

    }
}
