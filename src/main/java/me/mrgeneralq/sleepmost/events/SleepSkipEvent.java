package me.mrgeneralq.sleepmost.events;

import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;

import java.util.List;

public class SleepSkipEvent extends WorldEvent {

	private static final HandlerList HANDLERS = new HandlerList();
	private final SleepSkipCause cause;
	private final String lastSleeperName;
	private final String lastSleeperDisplayName;
	private final List<OfflinePlayer> peopleWhoSlept;

	public SleepSkipEvent(World world, List<OfflinePlayer> peopleWhoSlept , SleepSkipCause cause, String lastSleeperName, String lastSleeperDisplayName){
		super(world);
		this.cause = cause;
		this.lastSleeperName = lastSleeperName;
		this.lastSleeperDisplayName = lastSleeperDisplayName;
		this.peopleWhoSlept = peopleWhoSlept;
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

	public List<OfflinePlayer> getPeopleWhoSlept() {
		return peopleWhoSlept;
	}
}
