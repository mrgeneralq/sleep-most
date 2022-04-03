package me.mrgeneralq.sleepmost.models;

import me.mrgeneralq.sleepmost.enums.TimeCycle;
import org.bukkit.World;

public class WorldProperty {

    private boolean insomniaEnabled = false;
    private TimeCycle timeCycle = TimeCycle.UNKNOWN;


    public boolean isInsomniaEnabled() {
        return insomniaEnabled;
    }

    public void setInsomniaEnabled(boolean insomniaEnabled) {
        this.insomniaEnabled = insomniaEnabled;
    }

    public TimeCycle getTimeCycle() {
        return timeCycle;
    }

    public void setTimeCycle(TimeCycle timeCycle) {
        this.timeCycle = timeCycle;
    }

    public static WorldProperty getNewProperty(){
        return new WorldProperty();
    }


}
