package me.mrgeneralq.sleepmost.bootstrapper;

import com.google.inject.Inject;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.commands.SleepmostCommand;

public class CommandBootstrapper {

    private final Sleepmost plugin;
    private final SleepmostCommand sleepmostCommand;

    @Inject
    public CommandBootstrapper(
            Sleepmost plugin,
            SleepmostCommand sleepmostCommand
    ) {
        this.plugin = plugin;
        this.sleepmostCommand = sleepmostCommand;
    }

    public void bootstrap() {
        System.out.println("Bootstrapping the commands...");

        plugin.getCommand("sleepmost").setExecutor(sleepmostCommand);

    }
}
