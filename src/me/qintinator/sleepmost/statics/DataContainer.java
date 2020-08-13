package me.qintinator.sleepmost.statics;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class DataContainer {

    private static DataContainer instance;

    private HashSet<World> runningWorldsAnimation;
    private HashMap<World, HashSet<Player>> sleepingPlayers;

    private DataContainer(){

        runningWorldsAnimation = new HashSet<>();
        sleepingPlayers = new HashMap<>();

    };


    public static DataContainer getContainer(){

        if(instance == null)
            instance = new DataContainer();
        return instance;
    }


    public HashSet<World> getRunningWorldsAnimation() {
        return runningWorldsAnimation;
    }


    public void addSleepingPlayer(Player player){

        if(!sleepingPlayers.containsKey(player.getWorld())){
            sleepingPlayers.put(player.getWorld(), new HashSet<>());
        }

        sleepingPlayers.get(player.getWorld()).add(player);
    }

    public void removeSleepingPlayer(Player player){

        HashSet<Player> playerList = this.sleepingPlayers.get(player.getWorld());
        playerList.remove(player);

    }

    public List<Player> getSleepingPlayers(World world){
        return new ArrayList<Player>(sleepingPlayers.get(world));
    }

}
