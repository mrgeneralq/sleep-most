package me.qintinator.sleepmost.eventlisteners;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
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
	
	
	public OnSleep(Main main) {
		this.main = main;
		this.messageMapper = new ConfigMessageMapper(this.main);
	}
	
	
	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {
		
		if(main.getConfig().get("sleep.percentage-required") == null)
			return;
		
		if(Bukkit.getWorlds().get(0).getTime() < 12300)
			return;
		
		if(Bukkit.getWorlds().get(0).getTime() > 23850)
			return;
				
		float percentageRequired = main.getConfig().getInt("sleep.percentage-required");
		float onlinePlayers = Bukkit.getOnlinePlayers().size();
		float playersSleepRequired = (int) Math.ceil(((onlinePlayers /100) * percentageRequired));

		
		int sleepingPlayers = Bukkit.getOnlinePlayers().stream().filter(p -> p.isSleeping()).collect(Collectors.toList()).size() +1;
		float percentageSleeping = ((sleepingPlayers / onlinePlayers) * 100 );
		
		
		for(Player player: Bukkit.getOnlinePlayers()) {
			
			player.sendMessage(messageMapper.getMessage(ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT, true).replaceFirst("%sleeping%", Integer.toString(sleepingPlayers)).replaceAll("%required%", Integer.toString(Math.round(playersSleepRequired))));
			
		}
	
		if(!(percentageSleeping  >= percentageRequired))
			return;
			
		Bukkit.getWorlds().get(0).setTime(0);

		
		for(Player p : Bukkit.getOnlinePlayers())		
			p.sendMessage(messageMapper.getMessage(ConfigMessage.NIGHT_SKIPPED, true));
	
	}

}
