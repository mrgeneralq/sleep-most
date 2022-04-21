package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.templates.MessageTemplate;
import me.mrgeneralq.sleepmost.models.WorldProperty;
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
    private final IWorldPropertyService worldPropertyService;

    public InsomniaSubCommand(ISleepService sleepService, IFlagsRepository flagsRepository, IMessageService messageService, IWorldPropertyService worldPropertyService) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.messageService = messageService;
        this.worldPropertyService = worldPropertyService;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        Player player = (Player) sender;
        World world = player.getWorld();

        if(!sleepService.isEnabledAt(world)){
            this.messageService.sendMessage(player, messageService.getMessage(ConfigMessage.NOT_ENABLED_FOR_WORLD).build());
            return true;
        }

        if (!sleepService.isNight(world)) {
            String notNightMessage = this.messageService.getMessage(ConfigMessage.CMD_ONLY_DURING_NIGHT).build();
            this.messageService.sendMessage(player, notNightMessage);
            return true;
        }

        if (this.worldPropertyService.getWorldProperties(world).isInsomniaEnabled()) {
            String insomniaMessage = this.messageService.getMessage(ConfigMessage.INSOMNIA_ALREADY_ENABLED).build();
            this.messageService.sendMessage(player, insomniaMessage);
            return true;
        }

        WorldProperty property = this.worldPropertyService.getWorldProperties(world);
        property.setInsomniaEnabled(true);

        this.worldPropertyService.setWorldProperty(world, property);

        String insomniaMessage = this.messageService.getMessage(ConfigMessage.INSOMNIA_ENABLED).build();
        List<Player> sleepingPlayers = this.sleepService.getSleepers(world);

        for(Player p: sleepingPlayers){
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60,1));
            p.teleport(p.getLocation());
            String targetInsomniaMessage = this.messageService.getMessage(ConfigMessage.INSOMNIA_NOT_SLEEPY).build();
            this.messageService.sendMessage(p, targetInsomniaMessage);
        }

        this.messageService.sendMessage(sender, insomniaMessage);
        return true;

    }
}

