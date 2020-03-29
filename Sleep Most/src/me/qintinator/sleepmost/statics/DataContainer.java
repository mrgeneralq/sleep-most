package me.qintinator.sleepmost.statics;

import org.bukkit.World;

import java.util.HashSet;

public class DataContainer {

    private static DataContainer instance;

    private HashSet<World> runningWorldsAnimation;

    private DataContainer(){

        runningWorldsAnimation = new HashSet<>();

    };


    public static DataContainer getContainer(){
        if(instance == null)
            instance = new DataContainer();
        return instance;
    }


    public HashSet<World> getRunningWorldsAnimation() {
        return runningWorldsAnimation;
    }
}
