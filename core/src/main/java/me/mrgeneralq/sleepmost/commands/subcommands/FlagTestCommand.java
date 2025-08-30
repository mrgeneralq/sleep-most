package me.mrgeneralq.sleepmost.commands.subcommands;

import com.google.inject.Inject;
import me.mrgeneralq.sleepmost.models.SleepMostFlag;
import me.mrgeneralq.sleepmost.services.IFlagValueService;
import me.mrgeneralq.sleepmost.services.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class FlagTestCommand implements ISubCommand {

    private final IFlagValueService flagValueService;

    @Inject
    public FlagTestCommand(IFlagValueService flagValueService) {
        this.flagValueService = flagValueService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        SleepMostFlag<String> flag = new SleepMostFlag<>("test", String.class, "default");

        return true;
    }
}
