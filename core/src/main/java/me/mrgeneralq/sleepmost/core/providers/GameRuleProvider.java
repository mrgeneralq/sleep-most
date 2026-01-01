package me.mrgeneralq.sleepmost.core.providers;

import me.mrgeneralq.sleepmost.core.interfaces.IGameRuleService;

public class GameRuleProvider {

    private static IGameRuleService gameRuleService;

    public static void  registerGameRuleService(IGameRuleService service){
        gameRuleService = service;
    }

    public static IGameRuleService get(){
        return gameRuleService;
    }
}
