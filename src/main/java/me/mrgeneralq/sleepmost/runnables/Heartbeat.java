package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.enums.TimeCycle;
import me.mrgeneralq.sleepmost.events.TimeCycleChangeEvent;
import me.mrgeneralq.sleepmost.flags.LongerNightDurationFlag;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IInsomniaService;
import me.mrgeneralq.sleepmost.interfaces.ISleepMostWorldService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
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
    private final IFlagsRepository flagsRepository;

    public Heartbeat(ISleepService sleepService, ISleepMostWorldService sleepMostWorldService, IInsomniaService insomniaService, IFlagsRepository flagsRepository) {
        this.sleepService = sleepService;
        this.sleepMostWorldService = sleepMostWorldService;
        this.insomniaService = insomniaService;
        this.flagsRepository = flagsRepository;
    }

    @Override
    public void run() {

        for(World world: Bukkit.getWorlds().stream().filter(this.sleepService::isEnabledAt).collect(Collectors.toList()))
        {
            updateTimeCycle(world);

            if(ServerVersion.CURRENT_VERSION.supportsGameRules()){
            checkPlannedFreezeRequired(world);
            checkInsomniaResetRequired(world);
            checkFreezeRequired(world);
            checkUnfreezeRequired(world);
            }

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

        if(sleepMostWorld.isFrozen())
            return;

        if(!sleepMostWorld.isPlannedFrozen())
           return;

        if(!Time.aroundMidNight(time))
            return;

        int longerNightsDuration = this.flagsRepository.getLongerNightDurationFlag().getValueAt(world);

        if(longerNightsDuration <= 0)
            return;

            Calendar timeToResume = Calendar.getInstance();
            timeToResume.add(Calendar.SECOND, longerNightsDuration);
            this.sleepMostWorldService.freezeTime(world, timeToResume);
        }

        private void checkUnfreezeRequired(World world){

            SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);

            if(!sleepMostWorld.isFrozen())
                return;

            if(sleepMostWorld.getFrozenUntil().getTimeInMillis() > Calendar.getInstance().getTimeInMillis() && !sleepMostWorld.isTimeCycleAnimationIsRunning())
                return;

            this.sleepMostWorldService.unfreezeTime(world);
        }


        /*
        * Check if the freeze needs to be scheduled
         */
        private void checkPlannedFreezeRequired(World world){

            double time = world.getTime();
            if(!Time.aroundTime(Time.MID_NIGHT - 500, time))
                return;

            double duration = this.flagsRepository.getLongerNightDurationFlag().getValueAt(world);
            SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);

            if(duration <= 0)
                return;

            sleepMostWorld.setPlannedFrozen(true);
        }

    }
