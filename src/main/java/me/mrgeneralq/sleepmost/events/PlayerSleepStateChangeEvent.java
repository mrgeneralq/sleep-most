package me.mrgeneralq.sleepmost.events;

import me.mrgeneralq.sleepmost.enums.SleepState;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSleepStateChangeEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	private final Player player;
	private final SleepState sleepState;
	private boolean isCancelled = false;

	public PlayerSleepStateChangeEvent(Player player, SleepState sleepState){
		this.player = player;
		this.sleepState = sleepState;
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public Player getPlayer() {
		return player;
	}

	public SleepState getSleepState() {
		return sleepState;
	}


}
