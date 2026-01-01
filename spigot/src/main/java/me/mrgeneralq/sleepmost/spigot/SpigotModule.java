package me.mrgeneralq.sleepmost.spigot;

import me.mrgeneralq.sleepmost.core.interfaces.IPlatformModule;
import me.mrgeneralq.sleepmost.core.providers.GameRuleProvider;
import me.mrgeneralq.sleepmost.spigot.services.SpigotGameRuleService;

public class SpigotModule implements IPlatformModule {

    @Override
    public void setup() {
        if(GameRuleProvider.get() == null){
            GameRuleProvider.registerGameRuleService(new SpigotGameRuleService());
        }
    }
}
