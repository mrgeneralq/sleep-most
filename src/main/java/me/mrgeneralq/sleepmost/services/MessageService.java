package me.mrgeneralq.sleepmost.services;
import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.statics.ChatColorUtils;
import me.mrgeneralq.sleepmost.statics.ConfigMessageMapper;
import me.mrgeneralq.sleepmost.statics.Message;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrgeneralq.sleepmost.statics.ChatColorUtils.colorize;

public class MessageService implements IMessageService {

	private final IConfigRepository configRepository;
	private final ISleepService sleepService;

	public MessageService(IConfigRepository configRepository, ISleepService sleepService) {
		this.configRepository = configRepository;
		this.sleepService = sleepService;
	}

	@Override
	public String getMessage(ConfigMessage message, boolean includePrefix) {

		String prefix = configRepository.getPrefix();
		String messagePath = ConfigMessageMapper.getMapper().getMessagePath(message);
		String configMessage = configRepository.getString(messagePath);

		return this.getMessage(configMessage, includePrefix);
	}

	@Override
	public String getMessage(String message, boolean includePrefix) {
		if(message.isEmpty())
			return "";

		//add the prefix to the message
		if(includePrefix) 
		{
			String prefix = configRepository.getPrefix();

			if(!prefix.isEmpty()){
				message = String.format("%s %s", prefix, message);
			}
		}
		return colorize(message.trim());
	}


	@Override
	public void sendMessageToWorld(ConfigMessage message, World world) {
		for(Player p: world.getPlayers()){
			this.sendMessage(p, getMessage(message, true), false);
		}
	}

	@Override
	public ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause) {
		return cause == SleepSkipCause.STORM ? ConfigMessage.PLAYERS_LEFT_TO_SKIP_STORM : ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT;
	}

	@Override
	public String getPlayersLeftMessage(Player player, SleepSkipCause cause) {

		World world = player.getWorld();

		return getMessage(this.getSleepSkipCauseMessage(cause), false)
				.replaceFirst("%sleeping%", Integer.toString(sleepService.getPlayersSleepingCount(world)))
				.replaceAll("%required%", Integer.toString(Math.round(sleepService.getRequiredPlayersSleepingCount(world))))
				.replaceAll("%player%", player.getName());
	}

	@Override
	public void sendMessageToWorld(World world, String message) {
		for(Player p: world.getPlayers())
			this.sendMessage(p, message, true);
	}

	@Override
	public void sendPlayerLeftMessage(Player player, SleepSkipCause cause) {
		World world = player.getWorld();
		String message = this.getPlayersLeftMessage(player, cause);
		
		this.sendMessageToWorld(world, message);
	}
	
	@Override
	public void sendNightSkippedMessage(World world, String lastSleeperName, SleepSkipCause cause) {
		ConfigMessage message = (cause == SleepSkipCause.STORM ? ConfigMessage.STORM_SKIPPED : ConfigMessage.NIGHT_SKIPPED);
		
		String skipMessage = ConfigMessageMapper.getMapper().getMessage(message, false)
				.replace("%player%", lastSleeperName);
		
		sendMessageToWorld(world, skipMessage);
	}

	public void sendMessage(CommandSender sender, String message, boolean showPrefix){

		if(message.isEmpty())
			return;

		//add the prefix to the message
		if(showPrefix)
		{
			String prefix = configRepository.getPrefix();

			if(!prefix.isEmpty()) 
				message = String.format("%s %s", prefix, message);
		}

		sender.sendMessage(colorize(message));
	}
}
