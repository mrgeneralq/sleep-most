package me.mrgeneralq.sleepmost.spigot.services;

import me.mrgeneralq.sleepmost.core.interfaces.IGameRuleService;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.util.logging.Logger;

public class SpigotGameRuleService implements IGameRuleService {

    private static final Logger LOGGER = Logger.getLogger("SleepMost");
    private static boolean loggedUnsupported = false;

    @Override
    public void setAdvanceTime(World world, boolean value) {
        // ADVANCE_TIME only exists on newer Minecraft versions; skip gracefully
        // on servers that don't have it instead of failing to enable.
        try {
            world.setGameRule(GameRule.ADVANCE_TIME, value);
        } catch (LinkageError | RuntimeException error) {
            if (!loggedUnsupported) {
                loggedUnsupported = true;
                LOGGER.warning("The 'advanceTime' game rule is not available on this Minecraft version - skipping it. Cause: " + error);
            }
        }
    }
}
