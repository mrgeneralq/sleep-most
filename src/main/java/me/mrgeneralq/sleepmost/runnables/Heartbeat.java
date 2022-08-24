package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.enums.TimeCycle;
import me.mrgeneralq.sleepmost.events.TimeCycleChangeEvent;
import me.mrgeneralq.sleepmost.interfaces.IInsomniaService;
import me.mrgeneralq.sleepmost.interfaces.ISleepMostWorldService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.statics.Time;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.stream.Collectors;

public class Heartbeat extends BukkitRunnable {


    private final ISleepService sleepService;
    private final IInsomniaService insomniaService;
    private final ISleepMostWorldService sleepMostWorldService;

    public Heartbeat(ISleepService sleepService, ISleepMostWorldService sleepMostWorldService, IInsomniaService insomniaService) {
        this.sleepService = sleepService;
        this.sleepMostWorldService = sleepMostWorldService;
        this.insomniaService = insomniaService;
    }

    @Override
    public void run() {

        for(World world: Bukkit.getWorlds().stream().filter(this.sleepService::isEnabledAt).collect(Collectors.toList()))
        {
            updateTimeCycle(world);
            checkInsomniaResetRequired(world);
            checkFreezeRequired(world);

            SleepSkipCause cause = this.sleepService.getCurrentSkipCause(world);
            if(cause == SleepSkipCause.UNKNOWN|| cause == null){
                for(Player p: world.getPlayers().stream().filter(this.sleepService::isPlayerAsleep).collect(Collectors.toList())){
                    this.sleepService.setSleeping(p, false);
                }
            }
        }
    }

    private void checkInsomniaResetRequired(World world){

        SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);

        if(!sleepService.isNight(world)) {
            this.insomniaService.disableInsomnia(world);
        }
    }

    private void updateTimeCycle(World world){

        TimeCycle newTimeCycle = this.sleepService.isNight(world) ? TimeCycle.NIGHT : TimeCycle.DAY;
        SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);

        //prevent event from being fired unless TimeCycle is not unknown
        if(sleepMostWorld.getTimeCycle() == TimeCycle.UNKNOWN){
            sleepMostWorld.setTimeCycle(newTimeCycle);
            this.sleepMostWorldService.updateWorld(sleepMostWorld);
            return;
        }

        //only run if time cycle changes
        if(sleepMostWorld.getTimeCycle() == newTimeCycle)
            return;

        sleepMostWorld.setTimeCycle(newTimeCycle);
            this.sleepMostWorldService.updateWorld(sleepMostWorld);
            Bukkit.getPluginManager().callEvent(new TimeCycleChangeEvent(world, newTimeCycle));
    }

    private void checkFreezeRequired(World world){

        SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);
        double time = sleepMostWorld.getWorld().getTime();

        if(!sleepMostWorld.isPlannedFrozen())
            return;

        if(time != Time.MID_NIGHT && time != Time.NOON)
            return;

            int secondsToAdd = 20;

            Calendar timeToResume = Calendar.getInstance();
            timeToResume.add(Calendar.SECOND, secondsToAdd);
            this.sleepMostWorldService.freezeTime(world, timeToResume);
        }
    }
