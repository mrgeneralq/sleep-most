package me.mrgeneralq.sleepmost.models;

import me.mrgeneralq.sleepmost.enums.TimeCycle;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Calendar;
import java.util.UUID;

public class SleepMostWorld {

    private final UUID worldUUID;
    private boolean frozen = false;
    private boolean timeCycleAnimationIsRunning = false;

    private Calendar frozenUntil = null;
    private boolean plannedFrozen = false;
    private TimeCycle timeCycle = TimeCycle.UNKNOWN;


    public SleepMostWorld(World world){
        this.worldUUID = world.getUID();
    }

    public World getWorld(){
        return Bukkit.getWorld(this.worldUUID);
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen, Calendar until){
        this.plannedFrozen = false;
        this.frozen = frozen;
        this.frozenUntil = until;
    }

    public TimeCycle getTimeCycle() {
        return timeCycle;
    }

    public void setTimeCycle(TimeCycle timeCycle) {
        this.timeCycle = timeCycle;
    }

    public boolean isTimeCycleAnimation() {
        return timeCycleAnimationIsRunning;
    }

    public void setTimeCycleAnimation(boolean timeCycleAnimationIsRunning) {
        this.timeCycleAnimationIsRunning = timeCycleAnimationIsRunning;
    }

    public boolean isTimeCycleAnimationIsRunning() {
        return timeCycleAnimationIsRunning;
    }

    public void setTimeCycleAnimationIsRunning(boolean timeCycleAnimationIsRunning) {
        this.timeCycleAnimationIsRunning = timeCycleAnimationIsRunning;
    }

    public Calendar getFrozenUntil() {
        return frozenUntil;
    }

    public void setFrozenUntil(Calendar frozenUntil) {
        this.frozenUntil = frozenUntil;
    }

    public boolean isPlannedFrozen() {
        return plannedFrozen;
    }

    public void setPlannedFrozen(boolean plannedFrozen) {
        this.plannedFrozen = plannedFrozen;
    }
}
