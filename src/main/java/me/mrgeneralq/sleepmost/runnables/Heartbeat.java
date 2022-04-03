package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.IWorldPropertyService;
import me.mrgeneralq.sleepmost.models.WorldProperty;
import me.mrgeneralq.sleepmost.services.SleepService;
import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import me.mrgeneralq.sleepmost.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class Heartbeat extends BukkitRunnable {


    private final ISleepService sleepService;
    private final IWorldPropertyService worldPropertyService;

    public Heartbeat(ISleepService sleepService, IWorldPropertyService worldPropertyService) {
        this.sleepService = sleepService;
        this.worldPropertyService = worldPropertyService;
    }

    @Override
    public void run() {

        for(World world: Bukkit.getWorlds().stream().filter(this.sleepService::isEnabledAt).collect(Collectors.toList()))
        {

            checkInsomniaResetRequired(world);

            SleepSkipCause cause = this.sleepService.getCurrentSkipCause(world);
            if(cause == SleepSkipCause.UNKNOWN|| cause == null){
                for(Player p: world.getPlayers().stream().filter(this.sleepService::isPlayerAsleep).collect(Collectors.toList())){
                    this.sleepService.setSleeping(p, false);
                }
            }
        }
    }

    private void checkInsomniaResetRequired(World world){

        WorldProperty properties = this.worldPropertyService.getWorldProperties(world);

        if(!sleepService.isNight(world) && properties.isInsomniaEnabled()) {
            properties.setInsomniaEnabled(false);
            this.worldPropertyService.setWorldProperty(world, properties);
        }
    }
}
