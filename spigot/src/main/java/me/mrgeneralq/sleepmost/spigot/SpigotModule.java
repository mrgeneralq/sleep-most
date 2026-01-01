package me.mrgeneralq.sleepmost.spigot;

import me.mrgeneralq.sleepmost.core.interfaces.IPlatformModule;
import me.mrgeneralq.sleepmost.core.providers.GameRuleProvider;
import me.mrgeneralq.sleepmost.spigot.services.SpigotGameRuleService;
import org.bukkit.Bukkit;

public class SpigotModule implements IPlatformModule {

    @Override
    public void setup() {
        if(GameRuleProvider.get() == null){
            Bukkit.getLogger().info("Registering Spigot GameRule service");
            GameRuleProvider.registerGameRuleService(new SpigotGameRuleService());
        }
    }

    private boolean isSpigot(){
        try {
            Bukkit.getLogger().info("Registered spigot player listener");
            // This class exists on Spigot and Paper, but NOT on CraftBukkit
            Class.forName("org.bukkit.entity.Player$Spigot");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
