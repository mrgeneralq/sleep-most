package me.mrgeneralq.sleepmost.core.commands.subcommands;

import me.mrgeneralq.sleepmost.core.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.core.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.core.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.core.interfaces.ISubCommand;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

import static me.mrgeneralq.sleepmost.core.statics.ChatColorUtils.colorize;

public class TestCommand implements ISubCommand
{
    private final IMessageService messageService;
    private final IConfigRepository configRepository;
    private final IFlagsRepository flagsRepository;

    public TestCommand(IMessageService messageService, IFlagsRepository flagsRepository, IConfigRepository configRepository) {
        this.messageService = messageService;
        this.flagsRepository = flagsRepository;
        this.configRepository = configRepository;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(!(sender instanceof Player))
        {
            this.messageService.sendMessage(sender, ChatColor.RED + "You must be a player to run this.");
            return false;
        }
        Player player = (Player) sender;
        World world = player.getWorld();

        player.performCommand("sm enable");
        this.configRepository.setFlagValue(this.flagsRepository.getFeedFlag(), world, "hello");
        this.configRepository.setFlagValue(this.flagsRepository.getHealFlag(), world, "second value");
        this.configRepository.setFlagValue(this.flagsRepository.getNightcycleAnimationFlag(), world, null);
        this.configRepository.setFlagValue(this.flagsRepository.getCalculationMethodFlag(), world, null);
        player.performCommand("sm reload");

        return true;
    }


    private static String beautifulize(String text)
    {
        StringBuilder result = new StringBuilder();

        //add a random hex color before every letter
        for(char letter : text.toCharArray())
            result.append(randomHexColor()).append(letter);

        //colorize the hex colors
        return colorize(result.toString());
    }
    private static String randomHexColor()
    {
        StringBuilder color = new StringBuilder("#");

        //add random 6 hex digits
        for(int i = 1; i <= 6; i++)
            color.append(randomHexLetter());

        return color.toString();
    }
    private static char randomHexLetter()
    {
        Character min = null;
        Character max = null;

        switch(ThreadLocalRandom.current().nextInt(3))
        {
            case 0:
                min = 'a';
                max = 'f';
                break;
            case 1:
                min = 'A';
                max = 'F';
                break;
            case 2:
                min = '0';
                max = '9';
                break;
        }

        //returns a random character between 'min' and 'max'
        return (char) (min + ThreadLocalRandom.current().nextInt(max-min+1));
    }

}