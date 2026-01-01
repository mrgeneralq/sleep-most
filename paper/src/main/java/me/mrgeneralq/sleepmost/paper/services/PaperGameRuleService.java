package me.mrgeneralq.sleepmost.paper.services;

import me.mrgeneralq.sleepmost.core.interfaces.IGameRuleService;
import org.bukkit.GameRules;
import org.bukkit.World;

public class PaperGameRuleService implements IGameRuleService {
    @Override
    public void setAdvanceTime(World world, boolean value) {
        world.setGameRule(GameRules.ADVANCE_TIME, value);
    }
}
