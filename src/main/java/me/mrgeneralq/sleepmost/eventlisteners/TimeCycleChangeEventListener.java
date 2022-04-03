package me.mrgeneralq.sleepmost.eventlisteners;

import me.mrgeneralq.sleepmost.enums.TimeCycle;
import me.mrgeneralq.sleepmost.events.TimeCycleChangeEvent;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.IWorldPropertyService;
import me.mrgeneralq.sleepmost.models.WorldProperty;
import me.mrgeneralq.sleepmost.repositories.FlagsRepository;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class TimeCycleChangeEventListener implements Listener {


    private final ISleepService sleepService;
    private final IWorldPropertyService worldPropertyService;
    private final IFlagsRepository flagsRepository;

    public TimeCycleChangeEventListener(ISleepService sleepService, IWorldPropertyService worldPropertyService, IFlagsRepository flagsRepository) {

        this.sleepService = sleepService;
        this.worldPropertyService = worldPropertyService;
        this.flagsRepository = flagsRepository;
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

        WorldProperty properties = this.worldPropertyService.getWorldProperties(world);

        double insomniaChance = this.flagsRepository.getInsomniaChanceFlag().getValueAt(world);

        if(insomniaChance <= 0)
            return;

        boolean randomInsomnia = (insomniaChance >= new Random().nextDouble() * 1);

        if(!randomInsomnia)
            return;

            properties.setInsomniaEnabled(true);
            this.worldPropertyService.setWorldProperty(world, properties);
    }

}
