package me.qintinator.sleepmost.eventlisteners;
import me.qintinator.sleepmost.Main;
import me.qintinator.sleepmost.enums.ConfigMessage;
import me.qintinator.sleepmost.interfaces.*;
import me.qintinator.sleepmost.runnables.NightcycleAnimationTimer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class OnSleep implements Listener {

	private final Main main;
	private final ISleepService sleepService;
	private final IMessageService messageService;
	private final ICooldownService cooldownService;
	private final ISleepFlagService sleepFlagService;

	public OnSleep(Main main,ISleepService sleepService, IMessageService messageService, ICooldownService cooldownService, ISleepFlagService sleepFlagService) {
		this.sleepService = sleepService;
		this.messageService = messageService;
		this.cooldownService = cooldownService;
		this.sleepFlagService = sleepFlagService;
		this.main = main;
	}


	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {

		if(e.isCancelled())
			return;


		Player player = e.getPlayer();
		World world = player.getWorld();


		if (!sleepService.enabledForWorld(world)){
			return;
		}

		if (!sleepService.resetRequired(world)){
			return;
		}

		ISleepFlag<Boolean> stormSleepFlag = sleepFlagService.getSleepFlag("storm-sleep");
		if(stormSleepFlag.getValue(world) == false && world.isThundering()){
			messageService.sendMessage(player, messageService.getMessage(ConfigMessage.NO_SLEEP_THUNDERSTORM, false), true);
			e.setCancelled(true);
			return;
		}


		// getting the sleep flag
		ISleepFlag<Boolean> preventSleepFlag = sleepFlagService.getSleepFlag("prevent-sleep");


		if(preventSleepFlag.getValue(world)){
			messageService.sendMessage(player, messageService.getMessage(ConfigMessage.SLEEP_PREVENTED, true),false);
			e.setCancelled(true);
			return;
		}


		// check if player is cooling down, if not send message to world and start cooldown of player
		if(cooldownService.cooldownEnabled() && !cooldownService.isCoolingDown(player)){


			messageService.sendPlayerLeftMessage(player, sleepService.getSleepSkipCause(world));
			cooldownService.startCooldown(player);
		}


		if (!sleepService.sleepPercentageReached(world))
			return;


		ISleepFlag<Boolean> nightCycleAnimation = sleepFlagService.getSleepFlag("nightcycle-animation");
		if(nightCycleAnimation.getValue(world)){
			new NightcycleAnimationTimer(sleepService , messageService, world).runTaskTimer(main, 0,1);
			return;
		}

		sleepService.resetDay(world);
	}
}
