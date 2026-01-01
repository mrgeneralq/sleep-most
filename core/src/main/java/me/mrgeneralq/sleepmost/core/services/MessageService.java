package me.mrgeneralq.sleepmost.core.services;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.core.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.core.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.core.mappers.MessageMapper;
import me.mrgeneralq.sleepmost.core.models.ConfigMessage;
import me.mrgeneralq.sleepmost.core.repositories.MessageRepository;
import me.mrgeneralq.sleepmost.core.statics.ServerVersion;
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
	private final IFlagsRepository flagsRepository;
	private final MessageMapper messageMapper;
	private String prefix;

	//TODO remove IConfigRepo
	public MessageService(IConfigRepository configRepository, MessageRepository messageRepository, IFlagsRepository flagsRepository) {
		this.configRepository = configRepository;
		this.messageRepository = messageRepository;
		this.flagsRepository = flagsRepository;
		this.messageMapper = MessageMapper.getMapper();
		loadPrefix();
	}


	@Override
	public MessageKey getSleepSkipCauseMessage(SleepSkipCause cause) {
		return cause == SleepSkipCause.STORM ? MessageKey.PLAYERS_LEFT_TO_SKIP_STORM : MessageKey.PLAYERS_LEFT_TO_SKIP_NIGHT;
	}

	@Override
	public String getPlayersLeftMessage(Player player, SleepSkipCause cause, int sleepingPlayersAmount, int requiredPlayersAmount) {

		MessageKey skipCauseConfigMessage = this.getSleepSkipCauseMessage(cause);
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

		boolean kickingAllowed = this.flagsRepository.getAllowKickFlag().getValueAt(world);

		if(ServerVersion.CURRENT_VERSION.supportsClickableText() && kickingAllowed){

			for(Player p: Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld() == world).collect(Collectors.toList())){

				TextComponent playersLeftMessageComponent = new TextComponent(playersLeftMessage);

				if(p.hasPermission("sleepmost.kick")){

					MessageBuilder kickMessage = this.getMessage(MessageKey.KICK_OUT_BED);

					TextComponent component = new TextComponent(kickMessage.build() + " ");
					component.setColor(ChatColor.RED);
					component.setBold(true);
					component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/sleepmost kick %s", player.getName())));
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
		MessageKey message = (cause == SleepSkipCause.STORM ? MessageKey.STORM_SKIPPED : MessageKey.NIGHT_SKIPPED);

		for(Player p: world.getPlayers()){

			String skipMessage = this.getMessage(message)
					.setPlayer(p)
					.setPlaceHolder("%last-sleeper%", lastSleeperName)
					.setPlaceHolder("%dlast-sleeper%", lastSleeperDisplayName)
					.setWorld(world)
					.build();

			this.sendMessage(p , skipMessage);

		}
	}

	@Override
	public void sendNightSkippedMessage(List<OfflinePlayer> offlinePlayers,World world, String lastSleeperName, String lastSleeperDisplayName, SleepSkipCause cause) {
		MessageKey message = (cause == SleepSkipCause.STORM ? MessageKey.STORM_SKIPPED : MessageKey.NIGHT_SKIPPED);

		for(OfflinePlayer p: offlinePlayers.stream().filter(OfflinePlayer::isOnline).collect(Collectors.toList())){

			String skipMessage = this.getMessage(message)
					.setPlayer(p.getPlayer())
					.setPlaceHolder("%last-sleeper%", lastSleeperName)
					.setPlaceHolder("%dlast-sleeper%", lastSleeperDisplayName)
					.setWorld(world)
					.build();

			this.sendMessage(p.getPlayer() , skipMessage);

		}
	}

	@Override
	public MessageBuilder getMessage(MessageKey configMessage){

		ConfigMessage messageObject = MessageMapper.getMapper().getMessage(configMessage);
		String messageStr = this.messageRepository.get(messageObject.getPath());
		return new MessageBuilder(messageStr, this.prefix);

	}

	@Override
	public MessageBuilder getMessage(String message){
		return new MessageBuilder(message, this.prefix);
	}

	@Override
	public MessageBuilder getMessagePrefixed(MessageKey configMessage) {
		return this.getMessage(configMessage).usePrefix(true);
	}

	@Override
	public MessageBuilder getMessagePrefixed(String message) {
		return this.getMessage(message).usePrefix(true);
	}

	@Override
	public List<ConfigMessage> getMessages(){
		return this.messageMapper.getAllMessages();
	}

	@Override
	public boolean messagePathExists(String path){
		return this.messageRepository.exists(path);
	}

	@Override
	public void createMessage(ConfigMessage message){
		this.messageRepository.set(message.getPath(), message.getDefaultValue());
	}

	@Override
	public void createMissingMessages(){
			for(ConfigMessage message : this.getMessages()){
				if(this.messagePathExists(message.getPath()))
					continue;
				this.createMessage(message);
			}
			this.messageRepository.saveConfig();
	}

	@Override
	public void reloadConfig(){

		this.messageRepository.loadConfig();
		loadPrefix();
		this.createMissingMessages();

	}

	private void loadPrefix(){
		ConfigMessage prefixMessage = this.messageMapper.getMessage(MessageKey.PREFIX);
		this.prefix = this.messageRepository.get(prefixMessage.getPath());
	}
}
