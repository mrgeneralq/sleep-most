package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.models.enums.TimeCycle;
import me.mrgeneralq.sleepmost.events.TimeCycleChangeEvent;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IInsomniaService;
import me.mrgeneralq.sleepmost.interfaces.ISleepMostWorldService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class TimeCycleChangeEventListener implements Listener {


    private final ISleepService sleepService;
    private final ISleepMostWorldService sleepMostWorldService;
    private final IFlagsRepository flagsRepository;
    private final IInsomniaService insomniaService;

    public TimeCycleChangeEventListener(ISleepService sleepService, ISleepMostWorldService sleepMostWorldService, IFlagsRepository flagsRepository, IInsomniaService insomniaService) {

        this.sleepService = sleepService;
        this.sleepMostWorldService = sleepMostWorldService;
        this.flagsRepository = flagsRepository;
        this.insomniaService = insomniaService;
    }

    @EventHandler
    public void onTimeCycleChange(TimeCycleChangeEvent e){

        if(e.getTimeCycle() == TimeCycle.DAY)
            onDayStart(e.getWorld());

        if(e.getTimeCycle() == TimeCycle.NIGHT)
            onNightStart(e.getWorld());

    }


    private void onDayStart(World world){

    }

    private void onNightStart(World world){

       checkInsomnia(world);

    }


    private void checkInsomnia(World world){

        SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);
        double insomniaChance = this.flagsRepository.getInsomniaChanceFlag().getValueAt(world);

        if(insomniaChance <= 0)
            return;

        boolean randomInsomnia = (insomniaChance >= new Random().nextDouble() * 1);

        if(!randomInsomnia)
            return;

            this.insomniaService.enableInsomnia(world);
    }
}
