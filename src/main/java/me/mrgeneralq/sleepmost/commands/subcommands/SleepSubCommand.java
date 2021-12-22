package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.messages.MessageBuilder;
import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import me.mrgeneralq.sleepmost.statics.ServerVersion;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SleepSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IFlagsRepository flagsRepository;
    private final IMessageService messageService;
    private final ICooldownService cooldownService;
    private final IBossBarService bossBarService;

    public SleepSubCommand(ISleepService sleepService, IFlagsRepository flagsRepository, IMessageService messageService, ICooldownService cooldownService, IBossBarService bossBarService) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.messageService = messageService;
        this.cooldownService = cooldownService;
        this.bossBarService = bossBarService;
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

        if(ServerVersion.CURRENT_VERSION.supportsBossBars() && this.flagsRepository.getUseBossBarFlag().getValueAt(world)){

            this.bossBarService.setVisible(world, true);
            BossBar bossBar = this.bossBarService.getBossBar(world);

            SleepSkipCause cause = this.sleepService.getCurrentSkipCause(world);
            int sleepingPlayersAmount = this.sleepService.getSleepersAmount(world);
            int playersRequiredAmount = this.sleepService.getRequiredSleepersCount(world);

            String configBossBarTitle = this.messageService.getConfigMessage(ConfigMessage.BOSS_BAR_TITLE);
            String bossBarTitle = new MessageBuilder(configBossBarTitle, "")
                    .usePrefix(false)
                    .setSleepingCount(sleepingPlayersAmount)
                    .setSleepingRequiredCount(playersRequiredAmount)
                    .setCause(cause)
                    .build();

            bossBar.setTitle(bossBarTitle);
            bossBar.setProgress(sleepService.getSleepersPercentage(world));

            if(sleepingPlayersAmount == 0)
                this.bossBarService.getBossBar(world).setVisible(false);

        }

        this.messageService.sendMessage(player, this.messageService.fromTemplate(getStatusTemplate(updatedSleepStatus)));
        return true;
    }
    private MessageTemplate getStatusTemplate(boolean sleepingStatus){
        return sleepingStatus ? MessageTemplate.SLEEP_SUCCESS : MessageTemplate.NO_LONGER_SLEEPING;
    }
}

