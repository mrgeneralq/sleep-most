package me.mrgeneralq.sleepmost.models.hooks;

import dev.geco.gsit.api.GSitAPI;
import dev.geco.gsit.object.GStopReason;
import dev.geco.gsit.object.IGPose;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;

public class GsitHook extends Hook {

    public GsitHook() {
        super(SleepMostHook.GSIT, "GSit");
    }

    public void setSleepingPose(Player player, boolean sleeping){

        if(sleeping){
            GSitAPI.createPose(player.getLocation().getBlock().getRelative(BlockFace.DOWN), player, Pose.SLEEPING);
        }
        else {
            IGPose pose =  GSitAPI.getPoseByPlayer(player);
            if(pose == null)
                return;
            GSitAPI.removePose(pose, GStopReason.GET_UP);
        }
    }
}
