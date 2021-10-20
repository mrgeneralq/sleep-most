package me.mrgeneralq.sleepmost.events;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;

import java.util.List;

public class SleepSkipEvent extends WorldEvent {

	private static final HandlerList HANDLERS = new HandlerList();
	private final SleepSkipCause cause;
	private final String lastSleeperName;
	private final String lastSleeperDisplayName;
	
	public SleepSkipEvent(World world, SleepSkipCause cause){
		this(world, cause, null);
	}
	public SleepSkipEvent(World world, SleepSkipCause cause, Player player){
		super(world);
		this.cause = cause;
		this.lastSleeperName = player.getName();
		this.lastSleeperDisplayName = player.getDisplayName();
	}

	public SleepSkipEvent(World world, List<OfflinePlayer> peopleWhoSlept , SleepSkipCause cause, String lastSleeperName, String lastSleeperDisplayName){
		super(world);
		this.cause = cause;
		this.lastSleeperName = lastSleeperName;
		this.lastSleeperDisplayName = lastSleeperDisplayName;
	}

	public SleepSkipCause getCause() {
		return cause;
	}
	public String getLastSleeperName() {
		return this.lastSleeperName;
	}

	public String getLastSleeperDisplayName() {
		return lastSleeperDisplayName;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
