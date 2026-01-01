package me.mrgeneralq.sleepmost.core.commands.subcommands;

import me.mrgeneralq.sleepmost.core.enums.MessageKey;
import me.mrgeneralq.sleepmost.core.enums.SleepMostHook;
import me.mrgeneralq.sleepmost.core.hooks.GsitHook;
import me.mrgeneralq.sleepmost.core.interfaces.*;
import me.mrgeneralq.sleepmost.core.models.Hook;
import me.mrgeneralq.sleepmost.core.models.SleepMostWorld;
import me.mrgeneralq.sleepmost.core.templates.MessageTemplate;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SleepSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IFlagsRepository flagsRepository;
    private final IMessageService messageService;
    private final ISleepMostWorldService sleepMostWorldService;
    private final IInsomniaService insomniaService;
    private final IHookService hookService;

    public SleepSubCommand(ISleepService sleepService, IFlagsRepository flagsRepository, IMessageService messageService, ICooldownService cooldownService, IBossBarService bossBarService, ISleepMostWorldService sleepMostWorldService, IInsomniaService insomniaService, IHookService hookService) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.messageService = messageService;
        this.sleepMostWorldService = sleepMostWorldService;
        this.insomniaService = insomniaService;
        this.hookService = hookService;
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

        //If Gsit is enabled and the player is sleeping, we will use the GsitHook to set the sleeping pose.
        //GSit event listener will handle the setSleeping method.
        Optional<Hook> optionalGsitHook = this.hookService.getHook(SleepMostHook.GSIT);
        if(optionalGsitHook.isPresent() && this.flagsRepository.getGSitHookFlag().getValueAt(world) && this.flagsRepository.getGsitSleepCmdFlag().getValueAt(world)){
            GsitHook gsitHook = (GsitHook) optionalGsitHook.get();
            gsitHook.setSleepingPose(player, true);
        }else{
            this.sleepService.setSleeping(player, updatedSleepStatus);
        }
        return true;
    }
    private MessageTemplate getStatusTemplate(boolean sleepingStatus){
        return sleepingStatus ? MessageTemplate.SLEEP_SUCCESS : MessageTemplate.NO_LONGER_SLEEPING;
    }
}

