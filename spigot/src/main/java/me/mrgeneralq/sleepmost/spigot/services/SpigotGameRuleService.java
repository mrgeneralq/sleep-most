package me.mrgeneralq.sleepmost.spigot.services;

import me.mrgeneralq.sleepmost.core.interfaces.IGameRuleService;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.util.logging.Logger;

public class SpigotGameRuleService implements IGameRuleService {

    private static final Logger LOGGER = Logger.getLogger("sleep-most");
    private static boolean warnedUnsupported = false;

    /**
     * Sets the ADVANCE_TIME game rule for the specified world.
     * <p>
     * ADVANCE_TIME only exists on newer Minecraft versions. On servers that do
     * not have it, resolving the field throws {@link NoSuchFieldError}, so we
     * catch that (and any related error) and skip the rule instead of letting
     * the plugin fail on those versions.
     */
    @Override
    public void setAdvanceTime(World world, boolean value) {
        try {
            world.setGameRule(GameRule.ADVANCE_TIME, value);
        } catch (LinkageError | RuntimeException error) {
            warnUnsupportedOnce(error);
        }
    }

    private static void warnUnsupportedOnce(Throwable error) {
        if (warnedUnsupported) {
            return;
        }
        warnedUnsupported = true;
        LOGGER.warning("[sleep-most] The 'advanceTime' game rule is not available on this Minecraft version - skipping it. Cause: " + error);
    }
}
