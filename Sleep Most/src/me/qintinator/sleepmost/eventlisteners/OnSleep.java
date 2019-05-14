package me.qintinator.sleepmost.eventlisteners;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import me.qintinator.sleepmost.Main;
import me.qintinator.sleepmost.statics.ConfigMessageMapper;
import me.qintinator.sleepmost.statics.ConfigMessageMapper.ConfigMessage;



public class OnSleep implements Listener {

	private final Main main;
	private final ConfigMessageMapper messageMapper;

	float percentageRequired;
	float onlinePlayers;
	int sleepingPlayers; 
	float percentageSleeping; 
	float playersSleepRequired;

	public OnSleep(Main main) {
		this.main = main;
		this.messageMapper = new ConfigMessageMapper(this.main);

	}


	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {

		World world = Bukkit.getWorlds().get(0);

		this.percentageRequired = main.getConfig().getInt("sleep.percentage-required");
		this.onlinePlayers = Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld() == world).collect(Collectors.toList()).size();
		this.sleepingPlayers = Bukkit.getOnlinePlayers().stream().filter(p -> p.isSleeping()).collect(Collectors.toList()).size() +1;
		this.playersSleepRequired = (int) Math.ceil(((onlinePlayers /100) * percentageRequired));
		this.percentageSleeping = ((sleepingPlayers / onlinePlayers) * 100 );




		if(main.getConfig().get("sleep.percentage-required") == null)
			return;


		Player pl = e.getPlayer();


		// do nothing if player is not in main world
		if(pl.getWorld() != world)
			return;


		//return if it is not night or thundering
		if(!(isNight(world) || world.isThundering()))
			return;

		//from this point it is either night or thundering


		// send percentage message to all players
		for(Player player: Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld() == world).collect(Collectors.toList())) {

			if(isNight(world)) {	
				sendPlayersLeftMessage(player, ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT);
			}else {
				sendPlayersLeftMessage(player, ConfigMessage.PLAYERS_LEFT_TO_SKIP_STORM);
			}

		}


		// do not set world  time if not reached
		if(!(percentageRequired()))
			return;

		
		//at this point either night or storm WILL be skipped SEND MESSAGE FIRST
		// send message to each player that night/thunder is skipped
		for(Player p : Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld() == world).collect(Collectors.toList())) {
			if(isNight(p.getWorld())) {
				p.sendMessage(messageMapper.getMessage(ConfigMessage.NIGHT_SKIPPED, true));	
			}else {
				p.sendMessage(messageMapper.getMessage(ConfigMessage.STORM_SKIPPED, true));
			}
		}


		// check if it is night
		if(isNight(world)) {

			// set time to day
			world.setTime(0);

			// check also for thunder, set thunder to false
			if(world.isThundering())
				world.setThundering(false);
			world.setStorm(false);
		}else {
			// it is day, but thundering, set thunder to false
			world.setThundering(false);
			world.setStorm(false);
		}

	}


	private boolean isNight(World world) {

		return (world.getTime() > 12541 && world.getTime() < 23850);

	}

	private boolean percentageRequired() {

		return this.percentageSleeping  >= this.percentageRequired;
	}

	private void sendPlayersLeftMessage(Player player, ConfigMessage message) {
		player.sendMessage(messageMapper.getMessage(message, true)
				.replaceFirst("%sleeping%", Integer.toString(sleepingPlayers))
				.replaceAll("%required%", Integer.toString(Math.round(playersSleepRequired)))
				.replaceAll("%player%", player.getName()));	
	}

}
