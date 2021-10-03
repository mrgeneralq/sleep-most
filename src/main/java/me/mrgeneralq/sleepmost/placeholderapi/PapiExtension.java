package me.mrgeneralq.sleepmost.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IFlagService;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import org.bukkit.OfflinePlayer;

public class PapiExtension extends PlaceholderExpansion {

    private final Sleepmost plugin;
    private final IFlagsRepository flagsRepository;
    private final ISleepService sleepService;

    public PapiExtension(Sleepmost sleepmost, IFlagsRepository flagsRepository, ISleepService sleepService) {
        this.plugin = sleepmost;
        this.flagsRepository = flagsRepository;
        this.sleepService = sleepService;
    }
    @Override
    public String getIdentifier() {
        return "sleepmost";
    }

    @Override
    public String getAuthor() {
        return this.plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {

        //disable placeholders for non enabled worlds
        if(!sleepService.isEnabledAt(player.getPlayer().getWorld()))
            return "";

        if(identifier.endsWith("_flag")){

            if(!player.isOnline())
                return "";

            String flagName = identifier.replace("_flag", "");

           if(!this.flagsRepository.flagExists(flagName))
                return "";

           return String.valueOf(flagsRepository.getFlag(flagName).getValueAt(player.getPlayer().getWorld()));

        }

        if(identifier.equalsIgnoreCase("sleeping-percentage")){
            return String.valueOf(this.sleepService.getSleepersPercentage(player.getPlayer().getWorld()));
        }

        if(identifier.equalsIgnoreCase("sleeping-count")){
            return String.valueOf(this.sleepService.getRequiredSleepersCount(player.getPlayer().getWorld()));
        }

        if(identifier.equalsIgnoreCase("players-required")){
            return String.valueOf(this.sleepService.getRequiredSleepersCount(player.getPlayer().getWorld()));
        }

        if(identifier.equalsIgnoreCase("sleeping")){
            return String.valueOf(this.sleepService.isPlayerAsleep(player.getPlayer()));
        }

        return "";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }


}
