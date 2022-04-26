package me.mrgeneralq.sleepmost.commands;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.ICooldownService;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SleepCommand implements CommandExecutor {
    private final ISleepService sleepService;
    private final IMessageService messageService;
    private final ICooldownService cooldownService;
    private final IFlagsRepository flagsRepository;

    public SleepCommand(ISleepService sleepService, IMessageService messageService, ICooldownService cooldownService, IFlagsRepository flagsRepository) {
        this.sleepService = sleepService;
        this.messageService = messageService;
        this.cooldownService = cooldownService;
        this.flagsRepository = flagsRepository;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            this.messageService.sendMessage(sender, messageService.getMessage(MessageKey.NO_CONSOLE_COMMAND).build());
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("sleepmost.sleep")) {
            this.messageService.sendMessage(player, this.messageService.getMessage(MessageKey.NO_PERMISSION_COMMAND).build());
            return true;
        }
        World world = player.getWorld();

        if(!this.flagsRepository.getSleepCmdFlag().getValueAt(world)){

            this.messageService.sendMessage(player, this.messageService.getMessage(MessageKey.SLEEP_CMD_DISABLED).build());
            return true;
        }


        if(this.flagsRepository.getPreventSleepFlag().getValueAt(world)) {

            String sleepPreventedConfigMessage = messageService.getMessage(MessageKey.SLEEP_PREVENTED).build();

            this.messageService.sendMessage(player, messageService.getMessage(sleepPreventedConfigMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());
            return true;
        }

        if (!this.sleepService.resetRequired(world)) {
            this.messageService.sendMessage(player, messageService.getMessage(MessageKey.CANNOT_SLEEP_NOW).build());
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

        this.messageService.sendMessage(player, this.messageService.getMessage(getConfigMessage(updatedSleepStatus)).build());
        return true;
    }
    private MessageKey getConfigMessage(boolean sleepingStatus){
        return sleepingStatus ? MessageKey.SLEEP_SUCCESS : MessageKey.NO_LONGER_SLEEPING;
    }
}
