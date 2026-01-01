package me.mrgeneralq.sleepmost.paper;

import me.mrgeneralq.sleepmost.core.interfaces.IPlatformModule;
import me.mrgeneralq.sleepmost.core.providers.GameRuleProvider;
import me.mrgeneralq.sleepmost.paper.services.PaperGameRuleService;

public class PaperModule implements IPlatformModule {

    @Override
    public void setup(){
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            GameRuleProvider.registerGameRuleService(new PaperGameRuleService());
        } catch (ClassNotFoundException ignored) {}
    }
}
