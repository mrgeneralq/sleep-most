package me.mrgeneralq.sleepmost.hooks;

import dev.geco.gsit.api.GSitAPI;
import dev.geco.gsit.objects.GetUpReason;
import me.mrgeneralq.sleepmost.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.models.Hook;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;

public class GsitHook extends Hook {

    public GsitHook() {
        super(SleepMostHook.GSIT, "GSit");
    }

    public void setSleepingPose(Player player, boolean sleeping){
            if(sleeping)
            GSitAPI.createPose(player.getLocation().getBlock().getRelative(BlockFace.DOWN), player, Pose.SLEEPING);
            else
            GSitAPI.removePose(player, GetUpReason.GET_UP);
    }
}
