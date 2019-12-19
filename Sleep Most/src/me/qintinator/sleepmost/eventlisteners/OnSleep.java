package me.qintinator.sleepmost.eventlisteners;
import me.qintinator.sleepmost.enums.ConfigMessage;
import me.qintinator.sleepmost.interfaces.ICooldownRepository;
import me.qintinator.sleepmost.interfaces.ICooldownService;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISleepService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;



public class OnSleep implements Listener {

	private final ISleepService sleepService;
	private final IMessageService messageService;
	private final ICooldownService cooldownService;

	public OnSleep(ISleepService sleepService, IMessageService messageService, ICooldownService cooldownService) {
		this.sleepService = sleepService;
		this.messageService = messageService;
		this.cooldownService = cooldownService;
	}


	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {

		Player player = e.getPlayer();
		World world = player.getWorld();


		if (!sleepService.enabledForWorld(world)){
			return;
		}

		if (!sleepService.resetRequired(world)){
			return;
		}


		// check if player is cooling down, if not send message to world and start cooldown of player
		if(cooldownService.cooldownEnabled() && !cooldownService.isCoolingDown(player)){


			messageService.sendPlayerLeftMessage(player, sleepService.getSleepSkipCause(world));
			cooldownService.startCooldown(player);
		}


		if (!sleepService.sleepPercentageReached(world))
			return;

		// skip night or storm
		sleepService.resetDay(world);
	}
}
