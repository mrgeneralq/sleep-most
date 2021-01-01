package me.mrgeneralq.sleepmost.services;
import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.MessageTemplate;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.statics.ConfigMessageMapper;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MessageService implements IMessageService {

	private final IConfigRepository configRepository;
	private final ISleepService sleepService;
	private final ConfigMessageMapper messageMapper = ConfigMessageMapper.getMapper();

	public MessageService(IConfigRepository configRepository, ISleepService sleepService) {
		this.configRepository = configRepository;
		this.sleepService = sleepService;
	}


	/*
	* This method returns the value specified in the config based on the path defined in the ConfigMessageMapper
	* This should be the only entry point to retrieve messages from the config
	*/

	@Override
	public String getConfigMessage(ConfigMessage message) {

		String path = this.messageMapper.getMessagePath(message);
		return configRepository.getString(path);

	}

	@Override
	public void sendMessageToWorld(ConfigMessage message, World world) {

		String configMessage = this.messageMapper.getMessagePath(message);
		MessageBuilder messageBuilder = this.getNewBuilder(configMessage);

		String newMessage = messageBuilder.usePrefix(true).build();

		for(Player p: world.getPlayers())
			p.sendMessage(newMessage);

	}

	@Override
	public ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause) {
		return cause == SleepSkipCause.STORM ? ConfigMessage.PLAYERS_LEFT_TO_SKIP_STORM : ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT;
	}

	@Override
	public String getPlayersLeftMessage(Player player, SleepSkipCause cause) {

		World world = player.getWorld();
		ConfigMessage skipCauseConfigMessage = this.getSleepSkipCauseMessage(cause);
		String message = this.getConfigMessage(skipCauseConfigMessage);


		return getNewBuilder(message)
				.usePrefix(true)
				.setPlayer(player)
				.setPlaceHolder("%sleeping%", Integer.toString(sleepService.getPlayersSleepingCount(world)))
				.setPlaceHolder("%required%", Integer.toString(Math.round(sleepService.getRequiredPlayersSleepingCount(world))))
				.build();
	}

	@Override
	public void sendMessageToWorld(World world, String message) {
		String finalMessage = getNewBuilder(message)
				.usePrefix(true)
				.build();

		for(Player p: world.getPlayers())
			p.sendMessage(message);
	}

	@Override
	public void sendPlayerLeftMessage(Player player, SleepSkipCause cause) {
		World world = player.getWorld();
		String message = this.getPlayersLeftMessage(player, cause);
		this.sendMessageToWorld(world, message);
	}
	
	@Override
	public void sendNightSkippedMessage(World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause cause) {
		ConfigMessage message = (cause == SleepSkipCause.STORM ? ConfigMessage.STORM_SKIPPED : ConfigMessage.NIGHT_SKIPPED);
		
		String skipMessage = ConfigMessageMapper.getMapper().getMessage(message, false)
				.replace("%player%", lastSleeperName);
		
		sendMessageToWorld(world, skipMessage);
	}

	@Override
	public MessageBuilder getNewBuilder(String rawMessage) {
		return new MessageBuilder(rawMessage, this.configRepository.getPrefix());
	}

	@Override
	public MessageBuilder getNewBuilder(MessageTemplate messageTemplate){
		return getNewBuilder(messageTemplate.getMessage());
	}

	@Override
	public String getFromTemplate(MessageTemplate messageTemplate){
		return getNewBuilder(messageTemplate).build();
	}
}
