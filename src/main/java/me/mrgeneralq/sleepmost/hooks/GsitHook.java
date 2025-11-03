package me.mrgeneralq.sleepmost.hooks;

import dev.geco.gsit.api.GSitAPI;
import dev.geco.gsit.model.Pose;
import dev.geco.gsit.model.PoseType;
import dev.geco.gsit.model.StopReason;
import me.mrgeneralq.sleepmost.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.models.Hook;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class GsitHook extends Hook {

    public GsitHook() {
        super(SleepMostHook.GSIT, "GSit");
    }

    public void setSleepingPose(Player player, boolean sleeping){

        if(sleeping){
            GSitAPI.createPose(player.getLocation().getBlock().getRelative(BlockFace.DOWN), player, PoseType.LAY);
        }
        else {
            Pose pose = GSitAPI.getPoseByPlayer(player);
            if(pose == null)
                return;

            GSitAPI.removePose(pose, StopReason.GET_UP);
        }
    }
}
