package me.mrgeneralq.sleepmost.core.events;

import me.mrgeneralq.sleepmost.core.enums.TimeCycle;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimeCycleChangeEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	private final World world;
	private final TimeCycle timeCycle;

	public TimeCycleChangeEvent(World world, TimeCycle timeCycle){
		this.world = world;
		this.timeCycle = timeCycle;
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public World getWorld() {
		return world;
	}

	public TimeCycle getTimeCycle() {
		return timeCycle;
	}
}
