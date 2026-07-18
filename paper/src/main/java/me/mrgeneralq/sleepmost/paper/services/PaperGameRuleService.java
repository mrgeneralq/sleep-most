package me.mrgeneralq.sleepmost.paper.services;

import me.mrgeneralq.sleepmost.core.interfaces.IGameRuleService;
import org.bukkit.GameRules;
import org.bukkit.World;

import java.util.logging.Logger;

public class PaperGameRuleService implements IGameRuleService {

    private static final Logger LOGGER = Logger.getLogger("SleepMost");
    private static boolean loggedUnsupported = false;

    @Override
    public void setAdvanceTime(World world, boolean value) {
        // ADVANCE_TIME only exists on newer Minecraft versions; skip gracefully
        // on servers that don't have it instead of failing to enable.
        try {
            world.setGameRule(GameRules.ADVANCE_TIME, value);
        } catch (LinkageError | RuntimeException error) {
            if (!loggedUnsupported) {
                loggedUnsupported = true;
                LOGGER.warning("The 'advanceTime' game rule is not available on this Minecraft version - skipping it. Cause: " + error);
            }
        }
    }
}
