package me.mrgeneralq.sleepmost.core.services;
import me.mrgeneralq.sleepmost.core.interfaces.IGameRuleService;
import org.bukkit.GameRule;
import org.bukkit.World;

public class GameRuleService implements IGameRuleService {

    /*
        * Sets the ADVANCE_TIME game rule for the specified world.
     */
    @Override
    public void setAdvanceTime(World world, boolean value) {
        world.setGameRule(GameRule.ADVANCE_TIME, value);
    }


}
