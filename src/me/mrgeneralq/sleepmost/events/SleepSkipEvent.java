package me.mrgeneralq.sleepmost.events;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SleepSkipEvent extends Event {

	private final static HandlerList handlers = new HandlerList();
	private final World world;
	private final SleepSkipCause cause;
	private final String lastSleeperName;
	
	public SleepSkipEvent(World world, SleepSkipCause cause){
		this(world, cause, null);
	}
	public SleepSkipEvent(World world, SleepSkipCause cause, String lastSleeperName){
		this.world = world;
		this.cause = cause;
		this.lastSleeperName = lastSleeperName;
	}
	public SleepSkipCause getCause() {
		return cause;
	}
	public World getWorld(){
		return this.world;
	}
	public String getLastSleeperName() {
		return this.lastSleeperName;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
