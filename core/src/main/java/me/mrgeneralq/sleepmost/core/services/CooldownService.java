package me.mrgeneralq.sleepmost.core.services;

import me.mrgeneralq.sleepmost.core.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.core.interfaces.ICooldownRepository;
import me.mrgeneralq.sleepmost.core.interfaces.ICooldownService;
import org.bukkit.entity.Player;
import java.util.Date;

public class CooldownService implements ICooldownService {

    private final ICooldownRepository cooldownRepository;
    private final IConfigRepository configRepository;

    public CooldownService(ICooldownRepository cooldownRepository, IConfigRepository configRepository) {
        this.cooldownRepository = cooldownRepository;
        this.configRepository = configRepository;
    }

    @Override
    public boolean isCoolingDown(Player player) {

        if(!cooldownRepository.contains(player))
            return false;

        long cooldownStartDate = cooldownRepository.getPlayerCooldown(player);
        long now = new Date().getTime();
        long differenceInSeconds =  (now - cooldownStartDate) / 1000;

        return differenceInSeconds < configRepository.getCooldown();
    }

    @Override
    public void startCooldown(Player player) {
        cooldownRepository.setCooldown(player);
    }

    @Override
    public boolean cooldownEnabled() {
        return configRepository.getCooldown() > -1;
    }

    @Override
    public void removeCooldown(Player player) {
        cooldownRepository.removeCooldown(player);
    }
}
