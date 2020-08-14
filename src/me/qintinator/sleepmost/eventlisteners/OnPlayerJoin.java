package me.qintinator.sleepmost.eventlisteners;
import me.qintinator.sleepmost.interfaces.IUpdateService;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {

	private final IUpdateService updateService;

	public OnPlayerJoin(IUpdateService updateService) {
		this.updateService = updateService;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){

		Player player = e.getPlayer();

		if(player.hasPermission("sleepmost.alerts"))
			new Thread(() -> notifyNewUpdate(player)).start();
	}

	//Notify the player if this plugin has a new update which the server doesn't have
	private void notifyNewUpdate(Player player) 
	{
		if(updateService.hasUpdate()) 
		{
			player.sendMessage(Message.colorize("&bA newer version of &esleep-most &bis available! &cPlease note that support is only given to the latest version"));
		}
	}
}
