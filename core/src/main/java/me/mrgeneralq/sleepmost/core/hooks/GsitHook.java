package me.mrgeneralq.sleepmost.core.hooks;

import dev.geco.gsit.api.GSitAPI;
import dev.geco.gsit.model.Pose;
import dev.geco.gsit.model.PoseType;
import dev.geco.gsit.model.StopReason;
import me.mrgeneralq.sleepmost.core.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.core.models.Hook;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class GsitHook extends Hook {

    private static final Logger LOGGER = Logger.getLogger("SleepMost");

    // Only warn once to avoid log spam (this runs per sleeping player per skip).
    private static boolean loggedIncompatibility = false;

    public GsitHook() {
        super(SleepMostHook.GSIT, "GSit");
    }

    public void setSleepingPose(Player player, boolean sleeping){
        try {
            if (sleeping) {
                GSitAPI.createPose(player.getLocation().getBlock().getRelative(BlockFace.DOWN), player, PoseType.LAY);
            } else {
                Pose pose = GSitAPI.getPoseByPlayer(player);
                if (pose == null)
                    return;

                GSitAPI.removePose(pose, StopReason.GET_UP);
            }
        } catch (LinkageError | RuntimeException error) {
            /*
             * GSit is an optional integration. If the installed GSit version is
             * not API-compatible (e.g. NoSuchMethodError / NoClassDefFoundError
             * when its API has changed between releases) or otherwise fails, we
             * must not let it abort the SleepSkipEvent and break night skipping.
             * Degrade gracefully: skip the pose sync and warn once.
             */
            if (!loggedIncompatibility) {
                loggedIncompatibility = true;
                LOGGER.warning("Could not sync GSit sleeping pose - the installed GSit version may be "
                        + "incompatible with this build. Sleep skipping continues normally. Cause: " + error);
            }
        }
    }
}
