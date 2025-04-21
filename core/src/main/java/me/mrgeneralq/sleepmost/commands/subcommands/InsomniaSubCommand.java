package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.*;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class InsomniaSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IFlagsRepository flagsRepository;
    private final IMessageService messageService;
    private final ISleepMostWorldService sleepMostWorldService;
    private final ISleepMostPlayerService sleepMostPlayerService;
    private final IInsomniaService insomniaService;

    public InsomniaSubCommand(ISleepService sleepService,
                              IFlagsRepository flagsRepository,
                              IMessageService messageService,
                              ISleepMostWorldService sleepMostWorldService,
                              ISleepMostPlayerService sleepMostPlayerService,
                              IInsomniaService insomniaService
    ) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.messageService = messageService;
        this.sleepMostWorldService = sleepMostWorldService;
        this.sleepMostPlayerService = sleepMostPlayerService;
        this.insomniaService = insomniaService;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        Player player = (Player) sender;
        World world = player.getWorld();

        if(!sleepService.isEnabledAt(world)){
            this.messageService.sendMessage(player, messageService.getMessagePrefixed(MessageKey.NOT_ENABLED_FOR_WORLD)
                    .setWorld(world)
                    .build());
            return true;
        }

        if (!sleepService.isNight(world)) {
            String notNightMessage = this.messageService.getMessagePrefixed(MessageKey.CMD_ONLY_DURING_NIGHT)
                    .setWorld(world)
                    .build();
            this.messageService.sendMessage(player, notNightMessage);
            return true;
        }


        /*
        if (this.worldPropertyService.getWorldProperties(world).isInsomniaEnabled()) {
            String insomniaMessage = this.messageService.getMessagePrefixed(MessageKey.INSOMNIA_ALREADY_ENABLED)
                    .setWorld(world)
                    .build();
            this.messageService.sendMessage(player, insomniaMessage);
            return true;
        }
         */

        this.insomniaService.enableInsomnia(world);

        String insomniaMessage = this.messageService.getMessagePrefixed(MessageKey.INSOMNIA_ENABLED)
                .setWorld(world)
                .build();
        List<Player> sleepingPlayers = this.sleepService.getSleepers(world);

        for(Player p: sleepingPlayers){
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60,1));
            p.teleport(p.getLocation());
            String targetInsomniaMessage = this.messageService.getMessagePrefixed(MessageKey.INSOMNIA_NOT_SLEEPY)
                    .setWorld(world)
                    .build();
            this.messageService.sendMessage(p, targetInsomniaMessage);
        }

        this.messageService.sendMessage(sender, insomniaMessage);
        return true;

    }
}

