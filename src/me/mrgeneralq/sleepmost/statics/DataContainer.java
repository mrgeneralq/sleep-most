package me.mrgeneralq.sleepmost.statics;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

import java.util.*;

public class DataContainer {

    private static DataContainer instance;

    private Set<World> runningWorldsAnimation;
    private Map<World, Set<Player>> sleepingPlayers;

    private DataContainer(){

        runningWorldsAnimation = new HashSet<>();
        sleepingPlayers = new HashMap<>();

    }
    
    public static DataContainer getContainer(){
        if(instance == null)
            instance = new DataContainer();
        return instance;
    }


    public Set<World> getRunningWorldsAnimation() {
        return runningWorldsAnimation;
    }


    public void addSleepingPlayer(Player player){
    	this.sleepingPlayers.computeIfAbsent(player.getWorld(), p -> Sets.newHashSet()).add(player);
    }

    public void removeSleepingPlayer(Player player){
        Set<Player> playerList = this.sleepingPlayers.get(player.getWorld());
        playerList.remove(player);

    }

    public List<Player> getSleepingPlayers(World world){
        return new ArrayList<Player>(sleepingPlayers.get(world));
    }

}
