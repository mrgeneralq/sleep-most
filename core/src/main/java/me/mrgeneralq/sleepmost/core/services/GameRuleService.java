package me.mrgeneralq.sleepmost.core.services;

import me.mrgeneralq.sleepmost.core.interfaces.IGameRuleService;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.util.logging.Logger;

public class GameRuleService implements IGameRuleService {

    private static final Logger LOGGER = Logger.getLogger("SleepMost");
    private static boolean loggedUnsupported = false;

    /**
     * Sets the ADVANCE_TIME game rule for the specified world.
     * <p>
     * This game rule only exists on newer Minecraft versions. On servers that
     * don't have it, the field lookup throws {@link NoSuchFieldError}; we catch
     * that and skip gracefully so the plugin still enables.
     */
    @Override
    public void setAdvanceTime(World world, boolean value) {
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
