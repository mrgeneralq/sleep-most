package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.interfaces.IRepository;
import me.mrgeneralq.sleepmost.repositories.MessageRepository;
import me.mrgeneralq.sleepmost.templates.MessageTemplate;
import me.mrgeneralq.sleepmost.statics.ConfigMessageMapper;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class MessageService implements IMessageService {

	private final IConfigRepository configRepository;
	private final MessageRepository messageRepository;
	private final ConfigMessageMapper messageMapper = ConfigMessageMapper.getMapper();

	//TODO remove IConfigRepo
	public MessageService(IConfigRepository configRepository, MessageRepository messageRepository) {
		this.configRepository = configRepository;
		this.messageRepository = messageRepository;
	}

	@Override
	public String getConfigMessage(ConfigMessage message) {
		String path = this.messageMapper.getMessagePath(message);
		return configRepository.getString(path);
	}

	@Override
	public void sendWorldMessage(ConfigMessage message, World world) {

		String configMessage = this.messageMapper.getMessagePath(message);

		String newMessage = newPrefixedBuilder(configMessage).build();

		for(Player p: world.getPlayers())
			p.sendMessage(newMessage);

	}

	@Override
	public ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause) {
		return cause == SleepSkipCause.STORM ? ConfigMessage.PLAYERS_LEFT_TO_SKIP_STORM : ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT;
	}

	@Override
	public String getPlayersLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount) {

		ConfigMessage skipCauseConfigMessage = this.getSleepSkipCauseMessage(cause);
		String message = this.getConfigMessage(skipCauseConfigMessage);

		return newPrefixedBuilder(message)
				.usePrefix(false)
				.setPlayer(player)
				.setPlaceHolder("%sleeping%", String.valueOf(sleepingPlayersAmount))
				.setPlaceHolder("%required%", String.valueOf(requiredPlayersAmount))
				.setPlaceHolder("%remaining%", String.valueOf(requiredPlayersAmount - sleepingPlayersAmount))
				.build();
	}

	@Override
	public void sendMessage(CommandSender sender, String message)
	{
		if(message.isEmpty()) {
			return;
		}
		sender.sendMessage(message);
	}

	@Override
	public void sendWorldMessage(World world, String message) {

		if(message.isEmpty())
			return;

		String finalMessage = newPrefixedBuilder(message).build();

		for(Player p: world.getPlayers())
			p.sendMessage(finalMessage);
	}

	@Override
	public void sendWorldMessageWithPermission(World world, String permission, String message){
		for(Player p: world.getPlayers().stream().filter(p -> p.hasPermission(permission)).collect(Collectors.toList()))
			this.sendWorldMessage(world,message);
	}

	@Override
	public void sendWorldMessageWithPermission(World world, String permission, String messageWithPermission, String messageWithoutPermission){
		for(Player p: world.getPlayers()){
			String message = p.hasPermission(permission) ? messageWithPermission: messageWithoutPermission;
			this.sendWorldMessage(world, message);
		}
	}

	@Override
	public void sendOPMessage(String message)
	{
		if(message.isEmpty())
			return;

		Bukkit.getOperators().stream()
				.filter(OfflinePlayer::isOnline)
				.map(OfflinePlayer::getPlayer)
				.forEach(op -> op.sendMessage(message));
	}

	@Override
	public void sendPlayerLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount) {


		World world = player.getWorld();
		String playersLeftMessage = this.getPlayersLeftMessage(player, cause, sleepingPlayersAmount, requiredPlayersAmount);

		//TODO change to check compatible

		if(ServerVersion.CURRENT_VERSION.supportsClickableText()){

			for(Player p: Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld() == world).collect(Collectors.toList())){

				TextComponent playersLeftMessageComponent = new TextComponent(playersLeftMessage);

				if(p.hasPermission("sleepmost.kick")){
					TextComponent component = new TextComponent(" [kick out]");
					component.setColor(ChatColor.RED);
					component.setBold(true);
					component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/sm kick %s", player.getName())));
					playersLeftMessageComponent.addExtra(component);
				}
				p.spigot().sendMessage(playersLeftMessageComponent);
			}
			return;
		}

		//the server version does NOT Support clickable text
		this.sendWorldMessage(world, playersLeftMessage);

	}
	
	@Override
	public void sendNightSkippedMessage(World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause cause) {
		ConfigMessage message = (cause == SleepSkipCause.STORM ? ConfigMessage.STORM_SKIPPED : ConfigMessage.NIGHT_SKIPPED);

		String skipMessage = ConfigMessageMapper.getMapper().getMessage(message, false)
				.replace("%player%", lastSleeperName);

		sendWorldMessage(world, skipMessage);
	}

	@Override
	public MessageBuilder newBuilder(String rawMessage) {
		return new MessageBuilder(rawMessage, this.configRepository.getPrefix());
	}

	@Override
	public MessageBuilder newBuilder(MessageTemplate messageTemplate){
		return newBuilder(messageTemplate.getRawMessage());
	}

	@Override
	public MessageBuilder newPrefixedBuilder(String rawMessage)
	{
		return newBuilder(rawMessage)
				.usePrefix(true);
	}

	@Override
	public String fromTemplate(MessageTemplate messageTemplate){
		return newBuilder(messageTemplate.getRawMessage())
				.usePrefix(messageTemplate.usesPrefix())
				.build();
	}
}
