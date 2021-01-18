package me.mrgeneralq.sleepmost.commands;

import me.mrgeneralq.sleepmost.interfaces.ICooldownService;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SleepCommand implements CommandExecutor {
    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final ICooldownService cooldownService;

    public SleepCommand(ISleepService sleepService, IMessageService messageService, ICooldownService cooldownService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.cooldownService = cooldownService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.ONLY_PLAYERS_COMMAND));
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("sleepmost.sleep")) {
            player.sendMessage(this.messageService.fromTemplate(MessageTemplate.NO_PERMISSION));
            return true;
        }
        World world = player.getWorld();

        if (!this.sleepService.resetRequired(world)) {
            player.sendMessage(messageService.fromTemplate(MessageTemplate.CANNOT_SLEEP_NOW));
            return true;
        }
        boolean updatedSleepStatus = !this.sleepService.isPlayerAsleep(player);

        //update the sleeping status
        this.sleepService.setSleeping(player, updatedSleepStatus);

        // check if player is cooling down, if not send message to world and start cooldown of player
        if (cooldownService.cooldownEnabled() && !cooldownService.isCoolingDown(player)) {
            int sleepingPlayersAmount = sleepService.getSleepersAmount(world);
            int playersRequiredAmount = Math.round(sleepService.getRequiredSleepersCount(world));

            messageService.sendPlayerLeftMessage(player, sleepService.getCurrentSkipCause(world), sleepingPlayersAmount, playersRequiredAmount);
            cooldownService.startCooldown(player);
        }

        player.sendMessage(this.messageService.fromTemplate(getStatusTemplate(updatedSleepStatus)));
        return true;
    }
    private MessageTemplate getStatusTemplate(boolean sleepingStatus){
        return sleepingStatus ? MessageTemplate.SLEEP_SUCCESS : MessageTemplate.NO_LONGER_SLEEPING;
    }
}
