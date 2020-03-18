package me.qintinator.sleepmost.services;
import me.qintinator.sleepmost.enums.ConfigMessage;
import me.qintinator.sleepmost.enums.SleepSkipCause;
import me.qintinator.sleepmost.interfaces.IConfigRepository;
import me.qintinator.sleepmost.interfaces.IMessageService;
import me.qintinator.sleepmost.interfaces.ISleepService;
import me.qintinator.sleepmost.statics.ConfigMessageMapper;
import me.qintinator.sleepmost.statics.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        String prefix = configRepository.getPrefix();
        if(message.length() == 0)
            return "";

        if(prefix.length() > 0 && includePrefix){
            return String.format("%s %s", prefix, message);
        }

        return Message.getMessage(message.trim());
    }


    @Override
    public void sendMessageToWorld(ConfigMessage message, World world) {
        for(Player p: world.getPlayers()){
            this.sendMessage(p,getMessage(message, true), false);
        }
    }

    @Override
    public ConfigMessage getSleepSkipCauseMessage(SleepSkipCause cause) {
         if(cause == SleepSkipCause.Storm)
             return ConfigMessage.PLAYERS_LEFT_TO_SKIP_STORM;
         return ConfigMessage.PLAYERS_LEFT_TO_SKIP_NIGHT;
    }

    @Override
    public String getPlayersLeftMessage(Player player, SleepSkipCause cause) {

        World world = player.getWorld();
        return getMessage(this.getSleepSkipCauseMessage(cause),false)
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

    public void sendMessage(CommandSender sender, String message, boolean showPrefix){

        String prefix = configRepository.getPrefix();

        if(message.length() == 0)
            return;

        String fullMessage;
        fullMessage = message;

        if(showPrefix && prefix.length() > 0)
        fullMessage = String.format("%s %s", prefix, message);

        sender.sendMessage(Message.getMessage(fullMessage));
    }
}
