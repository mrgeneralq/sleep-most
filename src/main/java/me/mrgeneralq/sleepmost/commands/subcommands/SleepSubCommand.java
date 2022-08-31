package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.templates.MessageTemplate;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SleepSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IFlagsRepository flagsRepository;
    private final IMessageService messageService;
    private final ISleepMostWorldService sleepMostWorldService;
    private final IInsomniaService insomniaService;

    public SleepSubCommand(ISleepService sleepService, IFlagsRepository flagsRepository, IMessageService messageService, ICooldownService cooldownService, IBossBarService bossBarService, ISleepMostWorldService sleepMostWorldService, IInsomniaService insomniaService) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.messageService = messageService;
        this.sleepMostWorldService = sleepMostWorldService;
        this.insomniaService = insomniaService;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {


        Player player = (Player) sender;
        World world = player.getWorld();

        if(!this.flagsRepository.getSleepCmdFlag().getValueAt(world)){
            this.messageService.sendMessage(player, this.messageService.getMessagePrefixed(MessageKey.SLEEP_CMD_DISABLED).build());
            return true;
        }

        //check if sleep is allowed
        if(this.flagsRepository.getPreventSleepFlag().getValueAt(world)) {
            String sleepPreventedConfigMessage = messageService.getMessagePrefixed(MessageKey.SLEEP_PREVENTED)
                    .setWorld(world)
                    .setPlayer(player)
                    .build();
        }

        //check if reset is required
        if (!this.sleepService.isSleepingPossible(world)) {
            this.messageService.sendMessage(player, messageService.getMessagePrefixed(MessageKey.CANNOT_SLEEP_NOW)
                    .setWorld(world)
                    .build());
            return true;
        }

        if(this.insomniaService.hasInsomniaEnabled(player)){
            String insomniaMessage = this.messageService.getMessagePrefixed(MessageKey.INSOMNIA_NOT_SLEEPY)
                    .setWorld(world)
                    .build();
            this.messageService.sendMessage(player, insomniaMessage);
            return true;
        }


        SleepMostWorld sleepMostWorld = this.sleepMostWorldService.getWorld(world);

        if(sleepMostWorld.isFrozen()){

           String longerNightsSleepPreventedMsg = this.messageService.getMessagePrefixed(MessageKey.SLEEP_PREVENTED_LONGER_NIGHT)
                    .setWorld(world)
                    .setPlayer(player)
                    .build();
           this.messageService.sendMessage(player, longerNightsSleepPreventedMsg);
            return true;
        }

        boolean updatedSleepStatus = !this.sleepService.isPlayerAsleep(player);

        //TODO check this what the original getStatusTemplate is
      //  this.messageService.sendMessage(player, this.messageService.getMessage(getStatusTemplate(updatedSleepStatus)).build());

        this.sleepService.setSleeping(player, updatedSleepStatus);
        return true;
    }
    private MessageTemplate getStatusTemplate(boolean sleepingStatus){
        return sleepingStatus ? MessageTemplate.SLEEP_SUCCESS : MessageTemplate.NO_LONGER_SLEEPING;
    }
}

