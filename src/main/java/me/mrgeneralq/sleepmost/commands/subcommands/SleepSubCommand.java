package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SleepSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IFlagsRepository flagsRepository;
    private final IMessageService messageService;
    private final ICooldownService cooldownService;

    public SleepSubCommand(ISleepService sleepService, IFlagsRepository flagsRepository, IMessageService messageService, ICooldownService cooldownService) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.messageService = messageService;
        this.cooldownService = cooldownService;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {


        Player player = (Player) sender;

        World world = player.getWorld();

        if(!this.flagsRepository.getSleepCmdFlag().getValueAt(world)){
            this.messageService.sendMessage(player, this.messageService.fromTemplate(MessageTemplate.SLEEP_CMD_DISABLED));
            return true;
        }


        if(this.flagsRepository.getPreventSleepFlag().getValueAt(world)) {

            String sleepPreventedConfigMessage = messageService.getConfigMessage(ConfigMessage.SLEEP_PREVENTED);

            this.messageService.sendMessage(player, messageService.newPrefixedBuilder(sleepPreventedConfigMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());
            return true;
        }

        if (!this.sleepService.resetRequired(world)) {
            this.messageService.sendMessage(player, messageService.fromTemplate(MessageTemplate.CANNOT_SLEEP_NOW));
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

        this.messageService.sendMessage(player, this.messageService.fromTemplate(getStatusTemplate(updatedSleepStatus)));
        return true;
    }
    private MessageTemplate getStatusTemplate(boolean sleepingStatus){
        return sleepingStatus ? MessageTemplate.SLEEP_SUCCESS : MessageTemplate.NO_LONGER_SLEEPING;
    }
}

