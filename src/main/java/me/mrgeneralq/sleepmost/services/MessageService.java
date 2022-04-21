package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.mappers.MessageMapper;
import me.mrgeneralq.sleepmost.models.Message;
import me.mrgeneralq.sleepmost.repositories.MessageRepository;
import me.mrgeneralq.sleepmost.mappers.ConfigMessageMapper;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class MessageService implements IMessageService {

	private final IConfigRepository configRepository;
	private final MessageRepository messageRepository;
	private final MessageMapper messageMapper;
	private final String prefix;

	//TODO remove IConfigRepo
	public MessageService(IConfigRepository configRepository, MessageRepository messageRepository) {
		this.configRepository = configRepository;
		this.messageRepository = messageRepository;
		this.messageMapper = MessageMapper.getMapper();

		Message prefixMessage = this.messageMapper.getMessage(ConfigMessage.PREFIX);
		this.prefix = this.messageRepository.get(prefixMessage.getPath());
	}


	@Override
	public ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause) {
		return cause == SleepSkipCause.STORM ? ConfigMessage.PLAYERS_LEFT_TO_SKIP_STORM : ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT;
	}

	@Override
	public String getPlayersLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount) {

		ConfigMessage skipCauseConfigMessage = this.getSleepSkipCauseMessage(cause);
		MessageBuilder skipConfigMessageBuilder = this.getMessage(skipCauseConfigMessage)
				.usePrefix(false)
				.setPlayer(player)
				.setPlaceHolder("%sleeping%", String.valueOf(sleepingPlayersAmount))
				.setPlaceHolder("%required%", String.valueOf(requiredPlayersAmount))
				.setPlaceHolder("%remaining%", String.valueOf(requiredPlayersAmount - sleepingPlayersAmount));

		return skipConfigMessageBuilder.build();
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

		MessageBuilder builder = new MessageBuilder(message, this.prefix)
				.usePrefix(true);

		for(Player p: world.getPlayers())
			this.sendMessage(p, builder.build());
	}

	@Override
	public void sendOPMessage(String message)
	{
		if(message.isEmpty())
			return;

		Bukkit.getOperators().stream()
				.filter(OfflinePlayer::isOnline)
				.map(OfflinePlayer::getPlayer)
				.forEach(op -> this.sendMessage(op, message));
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
	public MessageBuilder getMessage(ConfigMessage configMessage){

		Message messageObject = MessageMapper.getMapper().getMessage(configMessage);
		String messageStr = this.messageRepository.get(messageObject.getPath());
		return new MessageBuilder(messageStr, this.prefix);

	}

	@Override
	public MessageBuilder getMessage(String message){
		return new MessageBuilder(message, this.prefix);
	}

	@Override
	public MessageBuilder getMessagePrefixed(ConfigMessage configMessage) {
		return this.getMessage(configMessage).usePrefix(true);
	}

	@Override
	public MessageBuilder getMessagePrefixed(String message) {
		return this.getMessage(message).usePrefix(true);
	}

	@Override
	public List<Message> getMessages(){
		return this.messageMapper.getAllMessages();
	}

	@Override
	public boolean messagePathExists(String path){
		return this.messageRepository.exists(path);
	}

	@Override
	public void createMessage(Message message){
		this.messageRepository.set(message.getPath(), message.getDefaultValue());
	}

	@Override
	public void createMissingMessages(){
			for(Message message : this.getMessages()){
				if(this.messagePathExists(message.getPath()))
					continue;
				this.createMessage(message);
			}
			this.messageRepository.saveConfig();
	}
}
