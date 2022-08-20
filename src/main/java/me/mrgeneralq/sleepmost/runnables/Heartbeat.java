package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.enums.TimeCycle;
import me.mrgeneralq.sleepmost.events.TimeCycleChangeEvent;
import me.mrgeneralq.sleepmost.interfaces.IInsomniaService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.IWorldPropertyService;
import me.mrgeneralq.sleepmost.models.WorldProperty;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;

public class Heartbeat extends BukkitRunnable {


    private final ISleepService sleepService;
    private final IWorldPropertyService worldPropertyService;
    private final IInsomniaService insomniaService;

    public Heartbeat(ISleepService sleepService, IWorldPropertyService worldPropertyService, IInsomniaService insomniaService) {
        this.sleepService = sleepService;
        this.worldPropertyService = worldPropertyService;
        this.insomniaService = insomniaService;
    }

    @Override
    public void run() {

        for(World world: Bukkit.getWorlds().stream().filter(this.sleepService::isEnabledAt).collect(Collectors.toList()))
        {

            updateTimeCycle(world);
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

        if(!sleepService.isNight(world)) {
            this.insomniaService.disableInsomnia(world);
        }
    }

    private void updateTimeCycle(World world){

        TimeCycle newTimeCycle = this.sleepService.isNight(world) ? TimeCycle.NIGHT : TimeCycle.DAY;
        WorldProperty properties = this.worldPropertyService.getWorldProperties(world);

        //prevent event from being fired unless TimeCycle is not unknown
        if(properties.getTimeCycle() == TimeCycle.UNKNOWN){
            properties.setTimeCycle(newTimeCycle);
            this.worldPropertyService.setWorldProperty(world, properties);
            return;
        }

        //only run if time cycle changes
        if(properties.getTimeCycle() == newTimeCycle)
            return;

            properties.setTimeCycle(newTimeCycle);
            this.worldPropertyService.setWorldProperty(world, properties);
            Bukkit.getPluginManager().callEvent(new TimeCycleChangeEvent(world, newTimeCycle));
    }
}
