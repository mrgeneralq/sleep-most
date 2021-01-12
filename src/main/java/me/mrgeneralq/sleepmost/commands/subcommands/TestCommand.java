package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.flags.MobNoTargetFlag;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.statics.ChatColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class TestCommand implements ISubCommand
{
    private final MobNoTargetFlag mobNoTargetFlag;

    public TestCommand(MobNoTargetFlag mobNoTargetFlag)
    {
        this.mobNoTargetFlag = mobNoTargetFlag;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You must be a player to run this.");
            return false;
        }
        Player player = (Player) sender;
        player.sendMessage("mob-no-target value: " + this.mobNoTargetFlag.getValueAt(player.getWorld()));
        //player.sendMessage(beautifulize("Sleepmost was created by ") + ChatColor.GREEN + "MrGeneralQ" + beautifulize(", and ") + ChatColor.GREEN + "Mizrahi" + beautifulize(" helped."));

        return true;
    }
    private static String beautifulize(String text)
    {
        StringBuilder result = new StringBuilder();

        //add a random hex color before every letter
        for(char letter : text.toCharArray())
        {
            result.append(randomHexColor()).append(letter);
        }

        //colorize the hex colors
        return ChatColorUtils.colorize(result.toString());
    }
    private static String randomHexColor()
    {
        StringBuilder color = new StringBuilder("#");

        //add random 6 hex digits
        for(int i = 1; i <= 6; i++)
        {
            color.append(randomHexLetter());
        }
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