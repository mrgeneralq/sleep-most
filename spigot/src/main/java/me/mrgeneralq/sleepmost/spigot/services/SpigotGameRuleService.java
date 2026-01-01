package me.mrgeneralq.sleepmost.spigot.services;

import me.mrgeneralq.sleepmost.core.interfaces.IGameRuleService;
import org.bukkit.GameRule;
import org.bukkit.World;

public class SpigotGameRuleService implements IGameRuleService {
    @Override
    public void setAdvanceTime(World world, boolean value) {
        world.setGameRule(GameRule.ADVANCE_TIME, value);
    }
}
